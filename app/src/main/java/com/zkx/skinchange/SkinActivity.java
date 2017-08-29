package com.zkx.skinchange;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

public class SkinActivity extends Activity {
    SkinFactory skinFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SkinResource.getInstance().init(this);
        skinFactory = new SkinFactory();
        //设置当前的Activity的解析xml的工具类
        LayoutInflaterCompat.setFactory(getLayoutInflater(),skinFactory);
    }



    //手动更换皮肤
    public void update(){
        skinFactory.update();
    }
}
