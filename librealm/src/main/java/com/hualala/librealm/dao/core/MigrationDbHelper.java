package com.hualala.librealm.dao.core;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MigrationDbHelper {

    public static final int VERSION = 0;
    public static final String DB_NAME = "\"myRealm.realm\"";

    public static void initConfig(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public static void initConfig2(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(DB_NAME)
                .schemaVersion(VERSION)
                .migration(new Migration())
                .build();
        Realm.setDefaultConfiguration(config);
    }


}
