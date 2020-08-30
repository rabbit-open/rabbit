package com.supets.pet.utils;

import java.net.URLDecoder;

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
        return request == null ? "" : request;
    }

}
