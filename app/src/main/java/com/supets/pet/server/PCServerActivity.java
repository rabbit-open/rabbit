package com.supets.pet.server;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
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
    private static AsyncHttpServer server = new AsyncHttpServer();
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

        hostname.setMovementMethod(LinkMovementMethod.getInstance());

        urlspanclick();

    }

    private void urlspanclick() {
        CharSequence str = hostname.getText();
        if (str instanceof Spannable) {
            int end = str.length();
            Spannable sp = (Spannable) hostname.getText();  //构建Spannable对象、继承Spanned、Spanned对象继承CharSequener
            URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);  //找出text中的a标签
            //SpannableStringBuilder、SpannableString对象跟String对象差不多、只是比String对象多setSpan，
            //可以给字符串设置样式、大小、背景色...而 SpannableStringBuilder跟SpannableString的关系就跟String跟StringBuffer关系一样
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            style.clearSpans();//should clear old spans
            for (URLSpan url : urls) {
                MyClickSpan myURLSpan = new MyClickSpan();
                myURLSpan.setUrls(url.getURL());
                //设置样式其中参数what是具体样式的实现对象，start则是该样式开始的位置，end对应的是样式结束的位置，
                // 参数 flags，定义在Spannable中的常量
                style.setSpan(myURLSpan, sp.getSpanStart(url), sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            hostname.setText(style);
        }
    }

    private class MyClickSpan extends ClickableSpan {
        private String url;

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
            super.updateDrawState(ds);
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        }

        public void setUrls(String url) {
            this.url = url;
        }
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
        urlspanclick();
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