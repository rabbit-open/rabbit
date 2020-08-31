package com.supets.pet.mocklib.core;


import android.database.Cursor;
import android.net.Uri;

import com.supets.pet.AppContext;
import com.supets.pet.mocklib.MockConfig;

public class MockDataMapper implements IMockDataMapper {


    @Override
    public boolean getMapper(String url) {

        Uri uri = Uri.parse("content://" + MockConfig.MOCK_PROVIDE_AUTHORITY + "/mockdata");
        Cursor cursor = AppContext.INSTANCE.getContentResolver().query(
                uri, null, null, new String[]{url}, null);
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

        Uri uri = Uri.parse("content://" + MockConfig.MOCK_PROVIDE_AUTHORITY + "/mockdata");
        Cursor cursor = AppContext.INSTANCE.getContentResolver()
                .query(uri, null, null, new String[]{url}, null);

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
