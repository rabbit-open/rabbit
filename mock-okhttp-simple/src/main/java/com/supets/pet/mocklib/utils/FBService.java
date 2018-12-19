package com.supets.pet.mocklib.utils;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.supets.pet.mocklib.widget.MockDataReceiver;

public class FBService extends Service {

    private MockDataReceiver mockDataReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mockDataReceiver = new MockDataReceiver();
        registerReceiver(new MockDataReceiver(), new IntentFilter(MockDataReceiver.MOCK_SERVICE_NETWORK));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mockDataReceiver);
        stopForeground(true);
    }
}