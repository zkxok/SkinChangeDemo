package com.zkx.skinchange;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;

public class MainActivity extends SkinActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void change(View view) {
        String path = new File(Environment.getExternalStorageDirectory(), "skin.apk").getAbsolutePath();
        SkinResource.getInstance().loadSkin(path);
        update();
    }
}
