package com.supets.pet.greendao;

import com.supets.commons.App;

public class SessionFactory {

    private static DaoSession daoSession;

    protected static DaoSession getDbSession() {

        if (daoSession == null) {
            synchronized (SessionFactory.class) {
                if (daoSession == null) {
                    MigrationSQLiteOpenHelper devOpenHelper = new MigrationSQLiteOpenHelper(App.INSTANCE, "event.db", null);
                    DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
                    daoSession = daoMaster.newSession();
                }
            }
        }
        return daoSession;
    }

}
