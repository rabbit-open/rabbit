package com.supets.pet.mock.wechaht;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ProvinceCityDAL {

    private String SQL_GetList;
    private DBService dbService;
    Context context;

    public ProvinceCityDAL(Context context) {

        context = context;
        SQL_GetList = "SELECT * FROM SnsInfo";// 获取所有记录
        dbService = DBService.getInstance(context);
    }


    // 根据页码获取所有记录
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void select() {
        Cursor cursor = null;

        try {
            cursor = dbService.query(SQL_GetList, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                byte[] contnes = cursor.getBlob(cursor.getColumnIndex("content"));
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                closeDB();
            }
        }
    }


    public void closeDB() {
        if (dbService != null) {
            dbService.close();
        }
    }

}