package com.supets.pet.mocklib.widget;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.supets.pet.mocklib.utils.Utils;

import java.util.HashMap;

public class MockDataReceiver extends BroadcastReceiver {
    public static final String MOCK_SERVICE_NETWORK = "mock.crash.network2";

    public static HashMap<String,String> bigMessage=new HashMap<>();
    public static HashMap<String,String> bigRequest=new HashMap<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (MOCK_SERVICE_NETWORK.equals(intent.getAction())) {
            Utils.showFloatView(intent);
        }
    }
}
