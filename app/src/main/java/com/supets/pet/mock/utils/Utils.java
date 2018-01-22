package com.supets.pet.mock.utils;

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
        return request;
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
}
