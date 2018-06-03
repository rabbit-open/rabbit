package com.supets.pet.mock.utils;

import android.text.TextUtils;
import android.widget.LinearLayout;

import com.supets.commons.App;
import com.supets.pet.jsonview.JSONViewHelper;
import com.supets.pet.jsonview.JSONViewHelper2;
import com.supets.pet.jsonview.JsonView;

import org.json.JSONException;
import org.json.JSONObject;

public final class FormatLogProcess {


    public static boolean isJson(String message) {
        try {
            new JSONObject(message);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 得到格式化json数据  退格用\t 换行用\r
     */
    public static String format(String jsonStr) {

        if (TextUtils.isEmpty(jsonStr)) {
            return jsonStr;
        }

        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int i = 0; i < jsonStr.length(); i++) {
            char c = jsonStr.charAt(i);
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }

        return jsonForMatStr.toString();

    }


    public static String formatJsonText2(String content) {

        try {
            return new JSONObject(content).toString();
        } catch (Exception e) {
            return content;
        }
    }

    public static String formatJsonTab(String content) {
        try {
            return format(new JSONObject(content).toString());
        } catch (Exception e) {
            return content;
        }
    }

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

    @Deprecated
    public static String formatJsonText(String json) {
        if (FormatLogProcess.isJson(json)) {
            try {
                JsonView jsonView = JSONViewHelper2.parse(json, new LinearLayout(App.INSTANCE));
                return JSONViewHelper.parse(jsonView);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json;
    }
}