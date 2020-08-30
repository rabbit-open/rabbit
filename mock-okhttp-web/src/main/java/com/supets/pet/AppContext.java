package com.supets.pet;

import android.annotation.SuppressLint;
import android.app.Application;

@SuppressLint("PrivateApi")
public class AppContext {
    public static final Application INSTANCE;

    public AppContext() {
    }

    static {
        Application app = null;

        try {
            app = (Application)Class.forName("android.app.AppGlobals").getMethod("getInitialApplication", new Class[0]).invoke((Object)null, new Object[0]);
            if(app == null) {
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
            }
        } catch (Exception var8) {
            var8.printStackTrace();

            try {
                app = (Application)Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]).invoke((Object)null, new Object[0]);
            } catch (Exception var7) {
                var7.printStackTrace();
            }
        } finally {
            INSTANCE = app;
        }

    }
}
