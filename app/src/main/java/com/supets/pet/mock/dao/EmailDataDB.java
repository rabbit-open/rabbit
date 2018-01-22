package com.supets.pet.mock.dao;

import android.support.annotation.NonNull;

import com.supets.pet.greendao.EmailDataDao;
import com.supets.pet.greendao.SessionFactory;
import com.supets.pet.mock.bean.EmailData;
import com.supets.pet.mock.config.Config;

import java.util.List;

public class EmailDataDB extends SessionFactory {


    private static final EmailDataDao emailDataDao = getDbSession().getEmailDataDao();


    public static List<EmailData> queryAll() {
        return emailDataDao.queryBuilder().list();
    }

    public static List<EmailData> queryEmailDataById(String id) {
        return emailDataDao.queryRaw("where _id = ?  ", id);
    }

    public static void insertEmailData(EmailData status) {
        List<EmailData> list =
                emailDataDao.queryRaw("where email = ? ", status.getEmail());
        if (list == null || list.isEmpty()) {
            emailDataDao.insert(status);
        }
    }

    public static void updateEmailData(EmailData status) {
        emailDataDao.update(status);
    }

    public static void deleteEmailData(EmailData status) {
        emailDataDao.delete(status);
    }

    public static void deleteAll() {
        emailDataDao.deleteAll();
    }

    @NonNull
    public static String[] getEmailList() {
        String[] list;
        List<EmailData> data = EmailDataDB.queryAll();
        if (data != null && data.size() > 0) {
            list = new String[data.size()];
            for (int i = 0; i < data.size(); i++) {
                list[i] = data.get(i).getName() + "<" + data.get(i).getEmail() + ">";
            }
        } else {
            list = new String[1];
            list[0] = "发件人<" + Config.getEmailName() + ">";
        }
        return list;
    }

}
