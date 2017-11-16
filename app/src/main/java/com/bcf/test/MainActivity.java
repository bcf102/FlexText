package com.bcf.test;

import android.app.Activity;
import android.os.Bundle;

import com.bcf.flex.text.FlexText;

public class MainActivity extends Activity {
    FlexText mFlexText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        setTestData();
    }

    private void setTestData(){
//        mFlexText.setPrefixText("\u00a5");
//        mFlexText.setSuffixText("50.12");
//        mFlexText.setMainText("欢迎使用|吉时家|的商品");
//        mFlexText.setPrefixColor("#ff0000");
//        mFlexText.setSuffixColor("#ff0000");
//        mFlexText.setFocusColor("#00ff00");
//        mFlexText.setTextSize(50);
    }
}
