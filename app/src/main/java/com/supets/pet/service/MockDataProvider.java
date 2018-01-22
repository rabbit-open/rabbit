package com.supets.pet.service;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.supets.commons.App;
import com.supets.pet.greendao.MigrationSQLiteOpenHelper;
import com.supets.pet.mock.config.Config;
import com.supets.pet.mockui.R;

public class MockDataProvider extends ContentProvider {

    private static final String AUTHORITY = "com.supets.pet.mockprovider";
    //匹配成功后的匹配码  
    private static final int MATCH_ALL_CODE = 100;
    private static final int MATCH_ONE_CODE = 101;
    private static UriMatcher uriMatcher;

    private SQLiteDatabase db;

    //在静态代码块中添加要匹配的 Uri  
    static {
        //匹配不成功返回NO_MATCH(-1)  
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        /**
         * uriMatcher.addURI(authority, path, code); 其中 
         * authority：主机名(用于唯一标示一个ContentProvider,这个需要和清单文件中的authorities属性相同) 
         * path:路径路径(可以用来表示我们要操作的数据，路径的构建应根据业务而定) 
         * code:返回值(用于匹配uri的时候，作为匹配成功的返回值) 
         */
        uriMatcher.addURI(AUTHORITY, "mockdata", MATCH_ALL_CODE);// 匹配记录集合
        uriMatcher.addURI(AUTHORITY, "mockdata/#", MATCH_ONE_CODE);// 匹配单条记录
    }

    @Override
    public boolean onCreate() {
        MigrationSQLiteOpenHelper devOpenHelper = new MigrationSQLiteOpenHelper(App.INSTANCE, "event.db", null);
        db = devOpenHelper.getWritableDatabase();
        return false;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if (!Config.getDebugMode()){
            return  null;
        }

        switch (uriMatcher.match(uri)) {
            case MATCH_ALL_CODE:
                return  db.rawQuery(getContext().getString(R.string.querysql), selectionArgs);
            case MATCH_ONE_CODE:
                break;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

}