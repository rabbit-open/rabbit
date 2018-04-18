package com.supets.pet.mock.wechaht;

import android.content.Context;
import android.database.Cursor;
import android.util.Base64;
import android.util.Log;

import java.net.URLDecoder;
import java.net.URLEncoder;

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
    public void select() {
        Cursor cursor = null;

        try {
            cursor = dbService.query(SQL_GetList, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                byte[] contnes = cursor.getBlob(cursor.getColumnIndex("content"));
//                System.out.print("-----------");
//
////                System.out.print(URLDecoder.decode(new String(contnes)));
//
//                System.out.print(WeiXinUtils.dealWeiXinDataOfCircle("", "", "", "", "", "", 3, 0, new String(contnes), ""));

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


    //char转化为byte
    public static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }

    //byte转换为char
    public static char byteToChar(byte[] b) {
        char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
        return c;
    }

    public void closeDB() {
        if (dbService != null) {
            dbService.close();
        }
    }

}