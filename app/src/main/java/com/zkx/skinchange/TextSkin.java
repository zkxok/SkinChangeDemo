package com.zkx.skinchange;

import android.view.View;
import android.widget.TextView;

/**
 * Created by zkx on 2017/8/29.
 */


public class TextSkin extends SkinInterface{
    public TextSkin(String attrName, int refId, String attrValueName, String attrType) {
        super(attrName, refId, attrValueName, attrType);
    }


    /**
     * 设置对应的属性
     * @param view
     */
    @Override
    public void apply(View view) {
        //具体的应用皮肤检测 丢给子类去实现
        if(view instanceof TextView){
            TextView textView = (TextView) view;
            //SkinResource.getInstance().getColor(resId)从插件里面拿到皮肤颜色
            textView.setTextColor(SkinResource.getInstance().getColor(resId));
        }

    }
}
