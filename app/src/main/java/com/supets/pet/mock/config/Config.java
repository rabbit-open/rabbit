package com.supets.pet.mock.config;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class Config extends BasePref {

    private static final String Name = "mock_config";

    private static final String DebugMode = "DebugMode";
    private static final String JsonSwitch = "JsonSwitch";
    private static final String ToastSwitch = "ToastSwitch";
    private static final String Toastinstance = "Toastinstance";
    private static final String Toast = "Toast";
    private static final String BaseAPI = "api_key";


    private static final String EmailName = "emailName";
    private static final String EmailPass = "emailPass";
    private static final String Url = "urls";


    public static void setEmailName(String emailName) {
        SharedPreferences.Editor editor = edit(Name);
        editor = editor.putString(EmailName, emailName);
        editor.commit();
    }

    public static String getEmailName() {
        SharedPreferences preferences = getPref(Name);
        return preferences.getString(EmailName, "xxx@163.com");
    }

    public static void setEmailPass(String emailName) {
        SharedPreferences.Editor editor = edit(Name);
        editor = editor.putString(EmailPass, emailName);
        editor.commit();
    }

    public static String getEmailPass() {
        SharedPreferences preferences = getPref(Name);
        return preferences.getString(EmailPass, "xxxxxx");
    }

    public static void setUrl(String url) {
        SharedPreferences.Editor editor = edit(Name);
        Set<String> strings = getUrl();
        strings.add(url);
        editor = editor.putStringSet(Url, strings);
        editor.commit();
        editor.apply();
    }

    public static Set<String> getUrl() {
        SharedPreferences preferences = getPref(Name);
        return preferences.getStringSet(Url, new HashSet<String>());
    }


    public static void clear() {
        BasePref.clear(Name);
    }

    public static void setDebugMode(boolean debugMode) {
        SharedPreferences.Editor editor = edit(Name);
        editor = editor.putBoolean(DebugMode, debugMode);
        editor.commit();
    }

    public static void setJsonSwitch(boolean jsonSwitch) {
        SharedPreferences.Editor editor = edit(Name);
        editor = editor.putBoolean(JsonSwitch, jsonSwitch);
        editor.commit();
    }

    public static void setBaseAPI(String baseAPI) {
        SharedPreferences.Editor editor = edit(Name);
        editor = editor.putString(BaseAPI, baseAPI);
        editor.commit();
    }

    public static boolean getDebugMode() {
        SharedPreferences preferences = getPref(Name);
        return preferences.getBoolean(DebugMode, false);
    }

    public static boolean getJsonSwitch() {
        SharedPreferences preferences = getPref(Name);
        return preferences.getBoolean(JsonSwitch, false);
    }

    public static String getBaseAPI() {
        SharedPreferences preferences = getPref(Name);
        return preferences.getString(BaseAPI, "http://api.test.com");
    }

    public static boolean isFilterGuice(String url) {
        String json = getBaseAPI();

        if (json == null || TextUtils.isEmpty(json.trim())) {
            return false;
        }

        String[] filters = json.split("\\s+");


        for (int i = 0; i < filters.length; i++) {
            Log.v("filters", url + "\n" + filters[i]);
            if (url.contains(filters[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean getToastSwitch() {
        SharedPreferences preferences = getPref(Name);
        return preferences.getBoolean(ToastSwitch, false);
    }

    public static void setToastSwitch(boolean toastSwitch) {
        SharedPreferences.Editor editor = edit(Name);
        editor = editor.putBoolean(ToastSwitch, toastSwitch);
        editor.commit();
    }

    public static void setToastinstance(boolean toastSwitch) {
        SharedPreferences.Editor editor = edit(Name);
        editor = editor.putBoolean(Toastinstance, toastSwitch);
        editor.commit();
    }

    public static boolean getToastinstance() {
        SharedPreferences preferences = getPref(Name);
        return preferences.getBoolean(Toastinstance, false);
    }

    public static boolean getToast() {
        SharedPreferences preferences = getPref(Name);
        return preferences.getBoolean(Toast, false);
    }

    public static void setToast(boolean toast) {
        SharedPreferences.Editor editor = edit(Name);
        editor = editor.putBoolean(Toast, toast);
        editor.commit();
    }
}