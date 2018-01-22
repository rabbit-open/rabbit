package com.supets.pet.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;


public  class MigrationSQLiteOpenHelper extends DaoMaster.OpenHelper {

    public MigrationSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.DEBUG = true;
        MigrationHelper.migrate(db,
                MockDataDao.class,
                CrashDataDao.class,
                LocalMockDataDao.class,
                EmailDataDao.class);
    }
}