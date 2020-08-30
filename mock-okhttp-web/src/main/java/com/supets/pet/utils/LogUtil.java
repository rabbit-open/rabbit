package com.supets.pet.utils;

import android.text.TextUtils;
import android.util.Log;
 
/**
 * 打印工具类
 * 可自动把调用位置所在的类名和方法名作为tag，并可设置打印级别
 * ldw
 */
public class LogUtil {
 
    //以下为打印级别，级别从低到高
    public static final int LOG_LEVEL_VERBOSE = 1;
    public static final int LOG_LEVEL_DEBUG = 2;
    public static final int LOG_LEVEL_INFO = 3;
    public static final int LOG_LEVEL_WARN = 4;
    public static final int LOG_LEVEL_ERROR = 5;
    public static final int LOG_LEVEL_NOLOG = 6;
 
    private static String AppName = "";
    private static boolean PrintLine = false;
    private static int LogLevel = LOG_LEVEL_VERBOSE;
 
    /**
     * 可在打印的TAG前添加应用名标识，不设置则不输出
     */
    public static void setAppName(String appName){
        AppName = appName;
    }
 
    /**
     * 是否输出打印所在的行数，默认不输出
     */
    public static void setPrintLine(boolean enable){
        PrintLine = enable;
    }
 
    /**
     * 设置打印级别，且只有等于或高于该级别的打印才会输出
     */
    public static void setLogLevel(int logLevel){
        LogLevel = logLevel;
    }
 
 
    public static void v(String msg){
        if(LogLevel <= LOG_LEVEL_VERBOSE) {
            String tag = generateTag();
            Log.v(tag, msg);
        }
    }
 
    public static void d(String msg){
        if(LogLevel <= LOG_LEVEL_DEBUG) {
            String tag = generateTag();
            Log.d(tag, msg);
        }
    }
 
    public static void i(String msg){
        if(LogLevel <= LOG_LEVEL_INFO) {
            String tag = generateTag();
            Log.i(tag, msg);
        }
    }
 
    public static void w(String msg){
        if(LogLevel <= LOG_LEVEL_WARN) {
            String tag = generateTag();
            Log.w(tag, msg);
        }
    }
 
    public static void e(String msg){
        if(LogLevel <= LOG_LEVEL_ERROR) {
            String tag = generateTag();
            Log.e(tag, msg);
        }
    }
 
 
    /**
     * 生成tag
     */
    private static String generateTag() {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        String tag = "%s.%s";
        if(!TextUtils.isEmpty(AppName)){
            tag = AppName + "__" + tag;
        }
        if(PrintLine){
            tag += "(Line:%d)";
            tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        }else{
            tag = String.format(tag, callerClazzName, caller.getMethodName());
        }
        return tag;
    }
 
}