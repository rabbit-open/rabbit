package com.supets.pet.mock.ui.home;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.supets.pet.alert.PullMsgService;


public class MockUiActivity extends TabLayoutBottomActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        update();
    }

    private void startMsgService() {
        Intent intent = new Intent(this, PullMsgService.class);
        // bindService(intent, msgconnect, Context.BIND_AUTO_CREATE);
        startService(intent);
    }


    private void stopMsgService() {
        unbindService(msgconnect);
    }

    private ServiceConnection msgconnect = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
