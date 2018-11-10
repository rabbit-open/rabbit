package com.supets.pet;

import android.app.Application;
import android.content.Intent;

import com.supets.pet.service.LogService;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpUtils.initClient(new OkHttpClient());
        startService(new Intent(this, LogService.class));
    }

}
