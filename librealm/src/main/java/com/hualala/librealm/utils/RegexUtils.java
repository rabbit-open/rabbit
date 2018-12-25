package com.hualala.librealm.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static List<String> find(String regex, String price) {
        List<String> data = new ArrayList<>();
        if (price == null) {
            return data;
        }
        Matcher match = Pattern.compile(regex).matcher(price);
        while (match.find()) {
            String content = match.group();
            data.add(content);
        }

        Log.v("test", data.size() + "");
        return data;
    }

}
