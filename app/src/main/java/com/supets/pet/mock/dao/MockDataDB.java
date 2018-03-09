package com.supets.pet.mock.dao;

import android.database.Cursor;

import com.supets.pet.greendao.MockDataDao;
import com.supets.pet.greendao.SessionFactory;
import com.supets.pet.mock.bean.MockData;

import java.util.ArrayList;
import java.util.List;

public class MockDataDB extends SessionFactory {

    private static final MockDataDao dao = getDbSession().getMockDataDao();

    public static List<MockData> queryAllMockData(String url) {
        return dao.queryBuilder().where(MockDataDao.Properties.Url.eq(url)).orderDesc(MockDataDao.Properties.Time).list();
    }

    public static List<MockData> queryAllMockDataPage(String url,int offset) {
        return dao.queryBuilder().where(MockDataDao.Properties.Url.eq(url)).orderDesc(MockDataDao.Properties.Time).offset(offset * 20).limit(20).list();
    }

    public static List<MockData> queryAll() {
        return dao.queryBuilder().orderDesc(MockDataDao.Properties.Time).list();
    }

    public static List<MockData> queryAllPage(int offset) {
        return dao.queryBuilder().orderDesc(MockDataDao.Properties.Time).offset(offset * 20).limit(20).list();
    }

    public static List<MockData> queryNewAllPage(int max) {
        return dao.queryBuilder().orderDesc(MockDataDao.Properties.Time).limit(max).list();
    }


    public static List<MockData> queryMockDataById(String id) {
        return dao.queryRaw("where _id = ?  ", id);
    }

    public static void insertMockData(MockData status) {
        dao.insert(status);
    }

    public static void updateMockData(MockData status) {
        dao.update(status);
    }

    public static void deleteMockData(MockData status) {
        dao.delete(status);
    }

    private static final String SQL_DISTINCT_ENAME = "SELECT DISTINCT " + MockDataDao.Properties.Url.columnName + " FROM " + MockDataDao.TABLENAME;

    public static List<String> queryAllUrl() {
        ArrayList<String> result = new ArrayList<String>();
        Cursor c = getDbSession().getDatabase().rawQuery(SQL_DISTINCT_ENAME, null);
        try {
            if (c.moveToFirst()) {
                do {
                    result.add(c.getString(0));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return result;
    }


    public static void deleteAll() {
        dao.deleteAll();
    }
}
