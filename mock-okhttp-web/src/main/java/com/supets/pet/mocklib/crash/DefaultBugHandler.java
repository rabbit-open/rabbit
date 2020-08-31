package com.supets.pet.mocklib.crash;

import com.supets.pet.mocklib.mock.JsonFormatUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

public class DefaultBugHandler implements UncaughtExceptionHandler {

    private UncaughtExceptionHandler mDefaultHandler;
    private static DefaultBugHandler INSTANCE;

    private DefaultBugHandler() {
    }

    public static DefaultBugHandler getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new DefaultBugHandler();
        }
        return INSTANCE;
    }

    public void init() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private boolean handleException(final Throwable ex, Thread thread) {
        if (ex == null) {
            return false;
        }

        ex.printStackTrace();

        if (CrashTimePref.isCrash()) {
            String file = saveBugFileInfo(ex);
            sendBroadCast(file);
        }

        return true;
    }

    private void sendBroadCast(String file) {
        JsonFormatUtils.sendLocalRequest("http://localhost/crash", file, "");
    }

    private String saveBugFileInfo(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        return sb.toString();
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex, thread) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

}