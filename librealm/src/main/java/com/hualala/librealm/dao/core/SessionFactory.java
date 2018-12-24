package com.hualala.librealm.dao.core;

import io.realm.Realm;

public class SessionFactory {

    private Realm mRealm;

    private Realm creatFactory() {
        if (mRealm == null) {
            mRealm = Realm.getDefaultInstance();
        }
        return mRealm;
    }

    public Realm getSession() {
        creatFactory();
        return mRealm;
    }


    public void close() {
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
            mRealm = null;
        }
    }

}
