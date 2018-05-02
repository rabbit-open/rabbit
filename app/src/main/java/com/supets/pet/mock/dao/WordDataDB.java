package com.supets.pet.mock.dao;

import android.database.Cursor;

import com.supets.pet.greendao.SessionFactory;
import com.supets.pet.greendao.WordDataDao;
import com.supets.pet.mock.bean.WordData;

import java.util.ArrayList;
import java.util.List;

public class WordDataDB extends SessionFactory {

    private static final WordDataDao dao = getDbSession().getWordDataDao();

    public static List<WordData> queryAllWordData(String url) {
        return dao.queryBuilder().where(WordDataDao.Properties.Name.eq(url)).list();
    }

    public static List<WordData> queryAllWordDataPage(String url, int offset) {
        return dao.queryBuilder().where(WordDataDao.Properties.Name.eq(url)).offset(offset * 20).limit(20).list();
    }

    public static List<WordData> queryAll() {
        return dao.queryBuilder().list();
    }


    public static void insertWordData(WordData status) {
        dao.insert(status);
    }

    public static void updateWordData(WordData status) {
        dao.update(status);
    }

    public static void deleteWordData(WordData status) {
        dao.delete(status);
    }

    private static final String SQL_DISTINCT_ENAME = "SELECT DISTINCT " + WordDataDao.Properties.Name.columnName + " FROM " + WordDataDao.TABLENAME;

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
