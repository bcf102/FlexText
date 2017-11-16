# FlexText
一个使用View实现的自定义文本控件。
方便灵活，控件由前缀字，主段落，后缀字三部分组成，每段文字可以单独设置颜色和尺寸，也可单独使用某一段或某两段。
可以很方便的在xml中设置属性或者在代码中设置属性，满足一些项目中一段字要显示多种颜色、尺寸的问题。
另外单独增加了金额显示功能，如果打开isCash开关，只设置主段落内容(app:text="25")的话，自动会格式化成￥25.00的样式，固定是人民币符号\u00a5。
如果想显示其他国家钱币符号请自行在(app:prefixText="\u00a5")中设置,同时可以单独设置颜色

一段文字中间需要高亮或者改变颜色可在字符串中增加指定分割符快速实现效果
    <com.bcf.flex.text.FlexText
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:prefixColor="@color/colorPrimary"
        app:suffixColor="@color/colorPrimary"
        app:text="欢迎光临|小猪佩奇|的魔幻城堡"
        app:textColor="@color/colorAccent"
        app:textSize="18dp" />
固定分割符为|， 欢迎光临|小猪佩奇|的魔幻城堡，这段布局的意思是前缀(欢迎光临)颜色(prefixColor)使用@color/colorPrimary颜色，主段落(小猪佩奇)颜色(textColor)使用@color/colorAccent，后缀（的魔幻城堡）颜色(suffixColor)使用@color/colorPrimary，分割符|会被自动删除掉

更多效果请下载Demo项目自行查看，代码中避免不了有BUG，还请各位大神海涵，非常感谢。   by gavin bi
