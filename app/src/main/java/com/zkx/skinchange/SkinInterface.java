package com.zkx.skinchange;

import android.view.View;

/**
 * Created by zkx on 2017/8/29.
 */

public abstract class SkinInterface {
    /**
     * 引入名字 background
     */
    String attrName = null;

    /**
     * id
     * R.color.ca
     */
    int resId = 0;

    /**
     * resource name资源名字 比如：@drawable
     */
    String attrValueName = null;

    /**
     * 类型名字比如 color和drawable
     */
    String attrType = null;

    public SkinInterface(String attrName,int refId,String attrValueName,String attrType){
        this.attrName = attrName;
        this.resId = refId;
        this.attrValueName = attrValueName;
        this.attrType = attrType;
    }

    /**
     * 应用换肤:具体的操作由子类去做
     * 子类(BackgroudSkin、TextSkin)
     *
     * @param view
     */
    public abstract void apply(View view);
}
