package com.supets.pet.mocklib.interceptor.cache;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.supets.pet.mocklib.AppContext;

class NetUtil {

    public static boolean isNetworkConnected() {
        Context context = AppContext.INSTANCE;
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

}
