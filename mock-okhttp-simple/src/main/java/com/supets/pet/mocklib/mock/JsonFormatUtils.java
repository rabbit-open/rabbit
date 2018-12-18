package com.supets.pet.mocklib.mock;

import android.content.Intent;
import android.util.Log;

import com.supets.pet.mocklib.AppContext;
import com.supets.pet.mocklib.widget.MockDataReceiver;

import org.json.JSONObject;

final class JsonFormatUtils {

    public static final String MOCK_SERVICE_NETWORK = "mock.crash.network";

    public static boolean isJson(String message) {
        try {
            new JSONObject(message);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void log(String url, String message) {
        Log.v("okhttp", message);
    }

    public static void logSave(String url, String message, String requestParam) {

        try {

            Intent intent = new Intent(MOCK_SERVICE_NETWORK);
            intent.putExtra("url", url);
            intent.putExtra("requestParam", requestParam);
            intent.putExtra("message", !isJpg(url) ? message : "jpg|png|jpeg|gif|apk");
            MockDataReceiver.onReceive(AppContext.INSTANCE, intent);

            if (isJson(message)) {
                String jsonStr = JsonFormatUtils.format(message);
                long totalpage = jsonStr.length() % 2048 == 0 ? 0 : 1 + jsonStr.length() / 2048;
                int start = 0;
                for (int i = 0; i < totalpage; i++) {
                    int dds = Math.min((i + 1) * 2048, jsonStr.length());
                    int last = jsonStr.indexOf(",", dds);
                    Log.v("okhttp-" + totalpage + "-" + getIntStr(i), "\n" + jsonStr.substring(start, last == -1 ? dds : (last + 1)));
                    start = last == -1 ? dds : (last + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logFile(String url, String message) {

    }


    static boolean isJpg(String url) {
        int end = url.lastIndexOf(".");
        return end >= 0 && url.substring(end + 1, url.length()).equalsIgnoreCase("jpg|png|jpeg|gif|apk");
    }


    public static String log(String message) {
        try {
            if (isJson(message)) {

                StringBuilder sb = new StringBuilder();

                String jsonStr = JsonFormatUtils.format(message);
                long totalpage = jsonStr.length() % 2048 == 0 ? 0 : 1 + jsonStr.length() / 2048;
                int start = 0;
                for (int i = 0; i < totalpage; i++) {
                    int dds = Math.min((i + 1) * 2048, jsonStr.length());
                    int last = jsonStr.indexOf(",", dds);
                    sb.append("\n" + jsonStr.substring(start, last == -1 ? dds : (last + 1)));
                    start = last == -1 ? dds : (last + 1);
                }
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    static String getIntStr(int page) {
        return (page < 10) ? "0" + page : page + "";
    }

    /**
     * get format json  data  tab \t change line \r
     */
    public static String format(String jsonStr) {
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

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

    public static void sendLocalRequest(String url, String requestParam, String message) {
        try {
            Intent intent = new Intent(MOCK_SERVICE_NETWORK);
            intent.putExtra("url", url);
            intent.putExtra("requestParam", requestParam);
            intent.putExtra("message", message);
            MockDataReceiver.onReceive(AppContext.INSTANCE, intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}