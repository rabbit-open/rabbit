package com.supets.pet.mock.db;

import android.content.Context;
import android.database.Cursor;

import com.supets.pet.mock.ui.translate.CharacterParser;
import com.supets.pet.mock.ui.translate.SortModel;

import java.util.ArrayList;

public class WordDao {

    private String all;
    private String findName;
    private DBService dbService;
    Context context;

    public WordDao(Context context) {

        context = context;
        all = "select distinct name  from  android";// 获取所有记录
        findName = "select distinct name  from  android where name like ? ";// 获取所有记录
        dbService = DBService.getInstance(context);
    }


    // 根据页码获取所有记录
    public ArrayList<SortModel> select() {
        Cursor cursor = null;
        ArrayList<SortModel> datas = new ArrayList<>();
        try {
            cursor = dbService.query(all, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                SortModel temp = new SortModel();
                temp.name = name;
                temp.sortLetters = CharacterParser.singleStringToLetter(name);
                datas.add(temp);
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

        return datas;
    }

    // 根据页码获取所有记录
    public ArrayList<SortModel> selectName(String like) {
        Cursor cursor = null;
        ArrayList<SortModel> datas = new ArrayList<>();
        try {
            cursor = dbService.query(findName, new String[]{"%" + like + "%"});
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                SortModel temp = new SortModel();
                temp.name = name;
                temp.sortLetters = CharacterParser.singleStringToLetter(name);
                datas.add(temp);
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

        return datas;
    }

    public boolean findNameByModule(String module, String name) {
        Cursor cursor = null;
        try {
            cursor = dbService.query("select *  from android where module=? and name=?;", new String[]{module, name});
            return cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                closeDB();
            }
        }

        return false;
    }

    public boolean addNewData(String module, String name) {
        try {
            dbService.execSQL("insert into android(module,name) values (?,?)", new String[]{module, name});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }

        return false;
    }

    public void closeDB() {
        if (dbService != null) {
            dbService.close();
        }
    }


}