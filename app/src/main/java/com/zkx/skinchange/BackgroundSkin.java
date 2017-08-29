package com.zkx.skinchange;

import android.view.View;

/**
 * Created by zkx on 2017/8/29.
 */

public class BackgroundSkin extends SkinInterface{
    public BackgroundSkin(String attrName, int refId, String attrValueName, String attrType) {
        super(attrName, refId, attrValueName, attrType);
    }


    @Override
    public void apply(View view) {
        /**
         * 判断背景颜色
         * 背景图片
         */
        if("color".equals(attrType)){
            view.setBackgroundColor(SkinResource.getInstance().getColor(resId));
        }else if("drawable".equals(attrType)){
            view.setBackgroundDrawable(SkinResource.getInstance().getDrawable(resId));
        }

    }
}
