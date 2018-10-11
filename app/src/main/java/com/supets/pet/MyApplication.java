package com.supets.pet;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpUtils.initClient(new OkHttpClient());
    }

//    @Override
//    public Resources getResources() { // 禁止APP字体跟随系统字体放大缩小
//        Resources res = super.getResources();
//        Configuration config = new Configuration();
//        config.setToDefaults();
//        config.densityDpi=320;
//        config.fontScale=1f;
//        try {
//            res.updateConfiguration(config, res.getDisplayMetrics());
//        } catch (Exception ignore) {
//            ignore.printStackTrace();
//        }
//        return res;
//    }
}
