package com.zkx.skinchange;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zkx on 2017/8/29.
 */

public class SkinFactory implements LayoutInflaterFactory {
    //系统控件前缀列表
    private static final String[] prefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit.",
    };
    //把所有需要换肤的控件进行缓存
    private Map<View, SkinItem> map = new HashMap<>();

    /**
     * setFactory后必走的方法(在里面拦截view的创建)
     * @param parent
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        Log.i("TAG", "name：" + name);
        View view = null;
        //判断是否是自定义控件
        if (name.indexOf(".") == -1) {//没有"."那么返回-1,是系统控件，不是自定义控件
            for (String prix : prefixList) { //prix:系统控件前缀
                //系统控件
                view = createView(context, attrs, prix + name);//一个一个拼接包名，看是否有那个View
                if (view != null) {//view不等于空说明拼接正确，有那个view，所以直接break跳出
                    break;
                }
            }
        } else {//是自定义控件
            //自定义控件->全包名
            view = createView(context, attrs, name);
        }
        //找到需要换肤的控件
        if (view != null) {
            parseSkinView(view, context, attrs);
        }
        return view;
    }

    //找到需要的换肤的控件 添加到集合
    private void parseSkinView(View view, Context context, AttributeSet attrs) {
        List<SkinInterface> attrsList = new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            //拿到属性名
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            int id = -1;
            String entryName;
            String typeName;
            SkinInterface skinInterface = null;
            //需要进行换肤
            if ("background".equals(attrName)) {
                id = Integer.parseInt(attrValue.substring(1));
                entryName = context.getResources().getResourceEntryName(id);
                typeName = context.getResources().getResourceTypeName(id);
                //封装成一个skinInterface
                skinInterface = new BackgroundSkin(attrName, id, entryName, typeName);
            } else if ("textColor".equals(attrName)) {
                id = Integer.parseInt(attrValue.substring(1));
                entryName = context.getResources().getResourceEntryName(id);
                typeName = context.getResources().getResourceTypeName(id);
                //封装成一个skinInterface
                skinInterface = new TextSkin(attrName, id, entryName, typeName);
            }
            if (skinInterface != null) {
                attrsList.add(skinInterface);
            }
        }
        //分装一个View 需要替换的资源集合
        SkinItem skinItem = new SkinItem(attrsList, view);
        map.put(view, skinItem);
        //在这里进行应用到底是皮肤资源，还是本地资源。、
        skinItem.apply();
    }


    /**
     * 去创建全类名为name的一个类(View)的实例
     *
     * @param context
     * @param attrs
     * @param name    控件全类名
     * @return
     */
    private View createView(Context context, AttributeSet attrs, String name) {
        try {
            //通过反射去创建全类名为name的一个类(View)的实例
            Class clazz = context.getClassLoader().loadClass(name);
            Constructor<? extends View> constructor = clazz.getConstructor(new Class[]{Context.class, AttributeSet.class});
            constructor.setAccessible(true);
            return constructor.newInstance(context, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给外面进行调用换肤
     */
    public void update() {
        //拿到所有需要换肤的View，然后遍历
        for (View view : map.keySet()) {
            if (view == null) {
                continue;
            }
            //拿到View需要改变的属性(颜色，背景),然后apply
            map.get(view).apply();
        }

    }

    /**
     * 每一个需要换肤的属性
     */
    class SkinItem {
        //每一个View，需要换肤的  集合
        public List<SkinInterface> attrList;

        public View view;

        public SkinItem(List<SkinInterface> attrList, View view) {
            this.attrList = attrList;
            this.view = view;
        }

        public void apply() {
            for (SkinInterface skinInterface : attrList) {
                //具体换肤丢给子类
                skinInterface.apply(view);
            }
        }
    }
}
