package com.supets.pet.mock.dao;

import com.supets.pet.greendao.CrashDataDao;
import com.supets.pet.greendao.SessionFactory;
import com.supets.pet.mock.bean.CrashData;

import java.util.List;

public class CrashDataDB extends SessionFactory {


    private static final CrashDataDao crashDataDao = getDbSession().getCrashDataDao();


    public static List<CrashData> queryAll() {
        return crashDataDao.queryBuilder().list();
    }

    public static List<CrashData> queryCrashDataById(String id) {
        return crashDataDao.queryRaw("where _id = ?  ", id);
    }

    public static void insertCrashData(CrashData status) {
        crashDataDao.insert(status);
    }

    public static void updateCrashData(CrashData status) {
        crashDataDao.update(status);
    }

    public static void deleteCrashData(CrashData status) {
        crashDataDao.delete(status);
    }

    public static void deleteAll() {
        crashDataDao.deleteAll();
    }

    public static List<CrashData> getCrashDataList() {
        return CrashDataDB.queryAll();
    }

}
