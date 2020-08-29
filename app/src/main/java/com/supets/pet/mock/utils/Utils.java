package com.supets.pet.mock.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.net.URLDecoder;
import java.util.HashMap;

public class Utils {

    public static String formatParam(String request) {
        StringBuffer sb = new StringBuffer();
        try {
            String[] params = request.split("&");
            for (String param : params) {
                sb.append(URLDecoder.decode(param)).append("\n");
            }
            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return request==null?"":request;
    }

    public static HashMap<String, String> formatHashMap(String request) {
        HashMap<String, String> map = new HashMap<>();
        try {
            String[] params = request.split("&");
            for (String param : params) {
                String[] keys = param.split("=");
                map.put(keys[0], URLDecoder.decode(keys[1]));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    public static void pushUrl(Context context, String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
