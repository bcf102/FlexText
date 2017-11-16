package com.bcf.flex.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义TextView控件，继承View,自己绘制，增加更多灵活方案
 * Created by bichuanfeng on 2017/11/15.
 */

public class FlexText extends View {
    private Paint mPaint = null;
    private boolean isCash = false; //是否是金额内容
    private int mHeight = 50; //控件默认高度
    private int mTextSize = 42; //默认字号
    private int mPrefixSize = 42; //前缀字号
    private int mSuffixSize = 42; //后缀字号

    private int mTextColor = Color.BLACK;//焦点字颜色
    private int mTextPrefixColor = Color.BLACK; //前缀字颜色
    private int mTextSuffixColor = Color.BLACK; //后缀字颜色

    private String mText = ""; //焦点字内容，从主体字内分割出来的
    private String mTextPrefix = ""; //前缀字
    private String mTextSuffix = ""; //后缀字
    private String mTempText = "";

    public FlexText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlexText);
        if (typedArray != null) {
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.FlexText_textSize, 42);//从布局文件中获取字号
            mPrefixSize = typedArray.getDimensionPixelSize(R.styleable.FlexText_prefixSize, 0);//从布局文件中获取字号
            mSuffixSize = typedArray.getDimensionPixelSize(R.styleable.FlexText_suffixSize, 0);//从布局文件中获取字号
            mText = typedArray.getString(R.styleable.FlexText_text);
            mTextPrefix = typedArray.getString(R.styleable.FlexText_prefixText);
            mTextSuffix = typedArray.getString(R.styleable.FlexText_suffixText);
            mTextColor = typedArray.getColor(R.styleable.FlexText_textColor, Color.BLACK);
            mTextPrefixColor = typedArray.getColor(R.styleable.FlexText_prefixColor, 0);
            mTextSuffixColor = typedArray.getColor(R.styleable.FlexText_suffixColor, 0);
            isCash = typedArray.getBoolean(R.styleable.FlexText_isCash, false);
            if (mPrefixSize == 0) {
                mPrefixSize = mTextSize;
            }
            if (mSuffixSize == 0) {
                mSuffixSize = mTextSize;
            }
            if (mTextPrefixColor == 0) {
                mTextPrefixColor = mTextColor;
            }
            if (mTextSuffixColor == 0) {
                mTextSuffixColor = mTextColor;
            }
            if(isCash){
                mText = format(mText);
                if(TextUtils.isEmpty(mTextPrefix)){
                    mTextPrefix = "\u00a5";
                }
            }else{
                setText(mText);
            }
            typedArray.recycle();
        }
    }

    /**
     * 设置是否是金额显示
     * @param flag
     */
    public void isCash(boolean flag){
        isCash = flag;
        if(isCash){
            mText = format(mText);
            if(TextUtils.isEmpty(mTextPrefix)){
                mTextPrefix = "\u00a5";
            }
        }
    }

    /**
     * 设置字体尺寸
     *
     * @param size
     */
    public void setTextSize(int size) {
        mTextSize = size;
    }

    /**
     * 设置主体字内容
     *
     * @param str
     */
    public void setText(String str) {
        if (str.contains("|")) {
            mTextPrefix = str.substring(0, str.indexOf("|"));
            if (getSymbolCount(str) > 1) {
                mText = str.substring(str.indexOf("|") + 1, str.lastIndexOf("|"));
                mTextSuffix = str.substring(str.lastIndexOf("|") + 1);
            } else {
                mText = str.substring(str.lastIndexOf("|") + 1);
            }
        }else{
            mText = str;
        }
    }

    /**
     * 设置前缀字内容
     *
     * @param str
     */
    public void setPrefixText(String str) {
        mTextPrefix = str;
    }

    /**
     * 设置焦点字内容
     *
     * @param str
     */
    public void setMainText(String str) {
        mText = format(str);
    }

    /**
     * 设置后缀字内容
     *
     * @param str
     */
    public void setSuffixText(String str) {
        mTextSuffix = str;
    }

    /**
     * 设置前缀字颜色
     *
     * @param color
     */
    public void setPrefixColor(String color) {
        mTextPrefixColor = Color.parseColor(color);
    }

    /**
     * 设置后缀字颜色
     *
     * @param color
     */
    public void setSuffixColor(String color) {
        mTextSuffixColor = Color.parseColor(color);
    }

    /**
     * 设置焦点字颜色
     *
     * @param color
     */
    public void setTextColor(String color) {
        mTextColor = Color.parseColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTempText = "";
        if (!TextUtils.isEmpty(mTextPrefix)) {
            mPaint.setTextSize(mPrefixSize); //重设字体尺寸
            mPaint.setColor(mTextPrefixColor); //设置前缀字颜色
            canvas.drawText(mTextPrefix, 0, mHeight, mPaint);
            mTempText+=mTextPrefix;
        }
        if (!TextUtils.isEmpty(mText)) {
            mPaint.setTextSize(mTextSize); //重设字体尺寸
            mPaint.setColor(mTextColor); //设置焦点字颜色
            canvas.drawText(mText, getTextWidth(mTextPrefix),  mHeight , mPaint);
            mTempText+=mText;
        }
        if (!TextUtils.isEmpty(mTextSuffix)) {
            mPaint.setTextSize(mSuffixSize); //重设字体尺寸
            mPaint.setColor(mTextSuffixColor); //设置后缀字颜色
            canvas.drawText(mTextSuffix, getTextWidth(mTextPrefix, mText), mHeight , mPaint);
            mTempText+=mTextSuffix;
        }
    }

    /**
     * 获取字符串宽度
     *
     * @param str
     * @return
     */
    private float getTextWidth(String... str) {
        float textWidth = 0;
        for (int i = 0; i < str.length; i++) {
            if (!TextUtils.isEmpty(str[i])) {
                textWidth += mPaint.measureText(str[i]) + 4;
            }
        }
        return textWidth;
    }

    /**
     * 获取字符串中有几个分割符|
     *
     * @param str
     * @return
     */
    private int getSymbolCount(String str) {
        int count = 0;
        char[] strs = str.toCharArray();
        for (char s : strs) {
            if (s == '|') {
                count++;
            }
        }
        return count;
    }

    /**
     * 格式化字符保留小数点后两位
     * @param f
     * @return
     */
    private String format(String f) {
        if(isNumeric(f)) {
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(Integer.parseInt(f));
        }else{
            return "0.00";
        }
    }

    /**
     * 检测字符是否为纯数字
     * @param str
     * @return
     */
    private boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches()){
            return false;
        }
        return true;
    }

    /**
     * 重新计算view的大小控制
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();
        int width = measureWidth(minimumWidth, widthMeasureSpec);
        int height = measureHeight(minimumHeight, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    private int measureWidth(int defaultWidth, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultWidth = (int) mPaint.measureText(mTempText) + getPaddingLeft() + getPaddingRight()+5;
                break;
            case MeasureSpec.EXACTLY:
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultWidth = Math.max(defaultWidth, specSize);
        }
        return defaultWidth;
    }


    private int measureHeight(int defaultHeight, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultHeight = (int) (-mPaint.ascent() + mPaint.descent()) + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);
                break;
        }
        return defaultHeight;
    }
}
