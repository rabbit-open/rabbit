package com.supets.pet.mock.ui.server;

import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.baidusoso.wifitransfer.Constants;
import com.baidusoso.wifitransfer.WifiConnectChangedReceiver;
import com.baidusoso.wifitransfer.WifiUtils;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.supets.pet.mockui.R;

public class PCServerActivity extends AppCompatActivity {
    private  static AsyncHttpServer server = new AsyncHttpServer();
    private static AsyncServer mAsyncServer = new AsyncServer();

    WifiConnectChangedReceiver mWifiConnectChangedReceiver = new WifiConnectChangedReceiver();

    private TextView hostname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcconfig);

        hostname = findViewById(R.id.hostname);

        RxBus.get().register(this);

        new ServerApi(this, server, mAsyncServer);

        show();
    }

    public void show() {
        checkWifiState(WifiUtils.getWifiConnectState(this));
        registerWifiConnectChangedReceiver();
    }

    void registerWifiConnectChangedReceiver() {
        IntentFilter intentFilter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(mWifiConnectChangedReceiver, intentFilter);
    }

    void unregisterWifiConnectChangedReceiver() {
        unregisterReceiver(mWifiConnectChangedReceiver);
    }

    @Subscribe(tags = {@Tag(Constants.RxBusEventType.WIFI_CONNECT_CHANGE_EVENT)})
    public void onWifiConnectStateChanged(NetworkInfo.State state) {
        checkWifiState(state);
    }

    void checkWifiState(NetworkInfo.State state) {
        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
            if (state == NetworkInfo.State.CONNECTED) {
                String ip = WifiUtils.getWifiIp(this);
                if (!TextUtils.isEmpty(ip)) {
                    onWifiConnected(ip);
                    return;
                }
            }
            onWifiConnecting();
            return;
        }
        onWifiDisconnected();
    }

    void onWifiDisconnected() {
        hostname.setText(R.string.fail_to_start_http_service);
    }

    void onWifiConnecting() {
        hostname.setText(R.string.retrofit_wlan_address);
    }

    void onWifiConnected(String ipAddr) {
        hostname.setText(getString(R.string.pcconfig, ipAddr));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (server != null) {
            server.stop();
        }
        if (mAsyncServer != null) {
            mAsyncServer.stop();
        }

        unregisterWifiConnectChangedReceiver();
        RxBus.get().unregister(this);
    }


}