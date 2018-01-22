package com.supets.pet.mock.dao;

import com.supets.pet.greendao.MockExampleDataDao;
import com.supets.pet.greendao.SessionFactory;
import com.supets.pet.mock.bean.MockExampleData;

import java.util.List;

public class MockExampleDataDB extends SessionFactory {

    private static final MockExampleDataDao dao = getDbSession().getMockExampleDataDao();

    public static List<MockExampleData> queryAllMockData(String name) {
        return dao.queryRaw("where name= ?", name);
    }

    public static List<MockExampleData> queryAllMockDataById(String id) {
        return dao.queryRaw("where _id= ?", id);
    }

    public static List<MockExampleData> queryAll() {
        return dao.loadAll();
    }

    public static void insertMockData(MockExampleData status) {
        dao.insert(status);
    }

    public static void updateMockData(MockExampleData status) {
        dao.update(status);
    }

    public static void deleteMockData(MockExampleData status) {
        dao.delete(status);
    }

    public static void deleteAll() {
        dao.deleteAll();
    }

}
