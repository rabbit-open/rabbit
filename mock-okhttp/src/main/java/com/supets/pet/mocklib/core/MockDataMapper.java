package com.supets.pet.mocklib.core;


import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContentResolverCompat;

import com.supets.pet.mocklib.AppContext;

public class MockDataMapper implements IMockDataMapper {

    private static final String AUTHORITY = "com.supets.pet.mockprovider";

    @Override
    public boolean getMapper(String url) {

        Uri uri = Uri.parse("content://" + AUTHORITY + "/mockdata");
        Cursor cursor = ContentResolverCompat.query(AppContext.INSTANCE.getContentResolver(),
                uri, null, null, new String[]{url}, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
                return cursor.getCount() > 0
                        && cursor.getInt(cursor.getColumnIndex("selected")) == 1;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    @Override
    public String getData(String url) {

        Uri uri = Uri.parse("content://" + AUTHORITY + "/mockdata");
        Cursor cursor = ContentResolverCompat.query(AppContext.INSTANCE.getContentResolver(),
                uri, null, null, new String[]{url}, null, null);

        try {
            if (cursor != null) {
                cursor.moveToFirst();
                return cursor.getString(cursor.getColumnIndex("data"));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}
