package com.supets.pet.mocklib.utils;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;

import com.supets.pet.mocklib.R;
import com.supets.pet.mocklib.widget.MockDataReceiver;

public class FBService extends Service {

    private MockDataReceiver mockDataReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mockDataReceiver = new MockDataReceiver();
        registerReceiver(new MockDataReceiver(), new IntentFilter(MockDataReceiver.MOCK_SERVICE_NETWORK));
        if (!NotificationManagerCompat.from(getApplicationContext()).areNotificationsEnabled()) {
            NotificationUtils.goToSet(getApplicationContext());
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= 26) {
            startForeground(1, new NotificationUtils(getApplicationContext(),
                    "channel_mock", getString(R.string.notify_channel_id), getString(R.string.notify_channel_name))
                    .createNotification(getString(R.string.notify_channel_title), getString(R.string.notify_channel_content)));
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mockDataReceiver);
        if (Build.VERSION.SDK_INT >= 26) {
            stopForeground(true);
        }
    }
}