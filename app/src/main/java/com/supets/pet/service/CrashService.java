package com.supets.pet.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.supets.lib.supetsrouter.uinav.UINav;
import com.supets.pet.mock.ui.MockCrashUiActivity;

public class CrashService extends BroadcastReceiver {

    public static final String MOCK_SERVICE_CRASH = "mock.crash.service";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (MOCK_SERVICE_CRASH.equals(intent.getAction())) {
            Intent mockIntent = new Intent(context, MockCrashUiActivity.class);
            mockIntent.putExtra("crashlog", intent.getStringExtra("crashlog"));
            UINav.pushStandard(context, mockIntent);
        }
    }
}
