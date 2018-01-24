package com.supets.pet.mock.ui.server;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by thinkpad on 2018/1/24.
 */

public class ServerUtils {

    public static  void getfiles(JSONArray array, File dir, String format) {

        if (TextUtils.isEmpty(format)) {
            return;
        }

        String[] fileNames = dir.list();
        if (fileNames != null) {
            for (String fileName : fileNames) {
                File file = new File(dir, fileName);

                if (file.exists() && file.isFile() && file.getName().endsWith("." + format)) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name", fileName);
                        jsonObject.put("path", file.getAbsolutePath());
                        array.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (file.exists() && file.isDirectory()) {
                    getfiles(array, file, format);
                }

            }
        }
    }


    public static  String getIndexContent(Context mContext,String name) throws IOException {
        BufferedInputStream bInputStream = null;
        try {
            bInputStream = new BufferedInputStream(mContext.getAssets().open(name));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] tmp = new byte[10240];
            while ((len = bInputStream.read(tmp)) > 0) {
                baos.write(tmp, 0, len);
            }
            return new String(baos.toByteArray(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (bInputStream != null) {
                try {
                    bInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
