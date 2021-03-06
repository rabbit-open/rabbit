package com.supets.pet.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.supets.pet.mock.bean.CrashData;
import com.supets.pet.mock.bean.EmailData;
import com.supets.pet.mock.bean.LocalMockData;
import com.supets.pet.mock.bean.MockData;
import com.supets.pet.mock.bean.MockExampleData;
import com.supets.pet.mock.bean.WordData;

import com.supets.pet.greendao.CrashDataDao;
import com.supets.pet.greendao.EmailDataDao;
import com.supets.pet.greendao.LocalMockDataDao;
import com.supets.pet.greendao.MockDataDao;
import com.supets.pet.greendao.MockExampleDataDao;
import com.supets.pet.greendao.WordDataDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig crashDataDaoConfig;
    private final DaoConfig emailDataDaoConfig;
    private final DaoConfig localMockDataDaoConfig;
    private final DaoConfig mockDataDaoConfig;
    private final DaoConfig mockExampleDataDaoConfig;
    private final DaoConfig wordDataDaoConfig;

    private final CrashDataDao crashDataDao;
    private final EmailDataDao emailDataDao;
    private final LocalMockDataDao localMockDataDao;
    private final MockDataDao mockDataDao;
    private final MockExampleDataDao mockExampleDataDao;
    private final WordDataDao wordDataDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        crashDataDaoConfig = daoConfigMap.get(CrashDataDao.class).clone();
        crashDataDaoConfig.initIdentityScope(type);

        emailDataDaoConfig = daoConfigMap.get(EmailDataDao.class).clone();
        emailDataDaoConfig.initIdentityScope(type);

        localMockDataDaoConfig = daoConfigMap.get(LocalMockDataDao.class).clone();
        localMockDataDaoConfig.initIdentityScope(type);

        mockDataDaoConfig = daoConfigMap.get(MockDataDao.class).clone();
        mockDataDaoConfig.initIdentityScope(type);

        mockExampleDataDaoConfig = daoConfigMap.get(MockExampleDataDao.class).clone();
        mockExampleDataDaoConfig.initIdentityScope(type);

        wordDataDaoConfig = daoConfigMap.get(WordDataDao.class).clone();
        wordDataDaoConfig.initIdentityScope(type);

        crashDataDao = new CrashDataDao(crashDataDaoConfig, this);
        emailDataDao = new EmailDataDao(emailDataDaoConfig, this);
        localMockDataDao = new LocalMockDataDao(localMockDataDaoConfig, this);
        mockDataDao = new MockDataDao(mockDataDaoConfig, this);
        mockExampleDataDao = new MockExampleDataDao(mockExampleDataDaoConfig, this);
        wordDataDao = new WordDataDao(wordDataDaoConfig, this);

        registerDao(CrashData.class, crashDataDao);
        registerDao(EmailData.class, emailDataDao);
        registerDao(LocalMockData.class, localMockDataDao);
        registerDao(MockData.class, mockDataDao);
        registerDao(MockExampleData.class, mockExampleDataDao);
        registerDao(WordData.class, wordDataDao);
    }
    
    public void clear() {
        crashDataDaoConfig.clearIdentityScope();
        emailDataDaoConfig.clearIdentityScope();
        localMockDataDaoConfig.clearIdentityScope();
        mockDataDaoConfig.clearIdentityScope();
        mockExampleDataDaoConfig.clearIdentityScope();
        wordDataDaoConfig.clearIdentityScope();
    }

    public CrashDataDao getCrashDataDao() {
        return crashDataDao;
    }

    public EmailDataDao getEmailDataDao() {
        return emailDataDao;
    }

    public LocalMockDataDao getLocalMockDataDao() {
        return localMockDataDao;
    }

    public MockDataDao getMockDataDao() {
        return mockDataDao;
    }

    public MockExampleDataDao getMockExampleDataDao() {
        return mockExampleDataDao;
    }

    public WordDataDao getWordDataDao() {
        return wordDataDao;
    }

}
