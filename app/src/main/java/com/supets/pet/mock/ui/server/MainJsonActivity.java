package com.supets.pet.mock.ui.server;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.server.AsyncHttpServer;

public class MainJsonActivity extends AppCompatActivity {
    private AsyncHttpServer server = new AsyncHttpServer();
    private AsyncServer mAsyncServer = new AsyncServer();
    private TextView webView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new TextView(this);
        webView.setText("浏览器输入地址：http://localhost:54321");
        setContentView(webView);
        new ServerApi(this, server, mAsyncServer);
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
    }


}