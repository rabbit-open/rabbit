package com.hualala.librealm;

import android.app.Application;

import com.hualala.librealm.dao.core.MigrationDbHelper;

public class RealmApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MigrationDbHelper.initConfig(this);
    }
}