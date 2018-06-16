package com.supets.pet.mock.dao;

import com.supets.pet.greendao.LocalMockDataDao;
import com.supets.pet.greendao.SessionFactory;
import com.supets.pet.mock.bean.LocalMockData;

import java.util.List;

public class LocalMockDataDB extends SessionFactory {


    private static final LocalMockDataDao dao = getDbSession().getLocalMockDataDao();


    public static List<LocalMockData> queryAllMockData(String url) {
        if (url != null && url.length() > 0) {
            int position = url.indexOf("?");
            if (position > 0) {
                url = url.substring(0, position);
            }
        }
        return dao.queryRaw("where url= ?", url);
    }

    public static void deleteById(String id) {
        dao.deleteByKey(Long.parseLong(id));
    }

    public static List<LocalMockData> queryAll() {
        return dao.loadAll();
    }

    public static void insertMockData(LocalMockData status) {
        dao.insert(status);
    }

    public static void updateMockData(LocalMockData status) {
        dao.update(status);
    }

    public static void deleteMockData(LocalMockData status) {
        dao.delete(status);
    }

    public static void deleteAll() {
        dao.deleteAll();
    }

    public static List<LocalMockData> queryMockDataById(String id) {
        return dao.queryRaw("where _id = ?  ", id);
    }

    public static List<LocalMockData> queryAllMockData(String url, String debug) {

        if (url != null && url.length() > 0) {
            int position = url.indexOf("?");
            if (position > 0) {
                url = url.substring(0, position);
            }
        }

        return dao.queryRaw("where url= ? and selected = ? ", url, Boolean.valueOf(debug)?"1":"0");
    }

    public static List<LocalMockData> queryAll(String debug) {
        return dao.queryRaw("where selected = ? ", Boolean.valueOf(debug)?"1":"0");
    }
}
