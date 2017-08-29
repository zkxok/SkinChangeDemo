package com.zkx.skinchange;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zkx on 2017/8/29.
 */

public class SkinResource {
    //外置卡的apkResources
    private Resources mSkinResource = null;
    //插件apk里面的包名
    private String mSkinPackage;

    private Context context;

    public void init(Context context) {
        this.context = context;
    }

    private static SkinResource ourInstance = new SkinResource();

    private SkinResource() {

    }

    public static SkinResource getInstance() {
        return ourInstance;
    }


    //外置卡的皮肤apk
    public void loadSkin(String path) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
            //拿到外置卡apk的信息
            mSkinPackage = packageInfo.packageName;

            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, path);
            //拿到本地的Resource
            Resources appResource = context.getResources();
            mSkinResource = new Resources(assetManager, appResource.getDisplayMetrics(), appResource.getConfiguration());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * 所有控件拿id通过这个方法
     * 不管是加载本地apk的资源 还是皮肤颜色资源 都通过这个方法
     *
     * @param resId
     * @return
     */
    public int getColor(int resId) {
        //代表本地无皮肤资源
        if (mSkinResource == null) {
            return resId;
        }

        //通过当前id找对应名字
        String resName = context.getResources().getResourceEntryName(resId);

        int trueId = mSkinResource.getIdentifier(resName, "color", mSkinPackage);
        //加载皮肤颜色apk的资源
        int color = mSkinResource.getColor(trueId);
        return color;
    }


    public Drawable getDrawable(int resId) {
        Drawable originDrawable = ContextCompat.getDrawable(context, resId);
        //代表本地无皮肤资源
        if (mSkinResource == null) {
            return originDrawable;
        }

        //通过当前id找对应名字
        String resName = context.getResources().getResourceEntryName(resId);
        int trueId = mSkinResource.getIdentifier(resName, "drawable", mSkinPackage);
        //加载皮肤颜色apk的资源
        Drawable trueDrawable = mSkinResource.getDrawable(trueId);
        return trueDrawable;
    }
}
