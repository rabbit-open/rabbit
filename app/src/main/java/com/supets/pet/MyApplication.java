package com.supets.pet;

import android.app.Application;
import android.content.IntentFilter;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.supets.pet.mock.utils.LogUtil;
import com.supets.pet.server.ServerApi;
import com.supets.pet.service.CrashService;
import com.supets.pet.service.MockDataReceiver;
import com.supets.pet.uctoast.ListenClipboardService;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {

    private static AsyncHttpServer server = new AsyncHttpServer();
    private static AsyncServer mAsyncServer = new AsyncServer();


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.setAppName("兔子测试");
        LogUtil.setLogLevel(LogUtil.LOG_LEVEL_VERBOSE);

        OkHttpUtils.initClient(new OkHttpClient());
        //启动服务
        ListenClipboardService.start(this);
        new ServerApi(this, server, mAsyncServer);
        //注册广播
        registerReceiver(new MockDataReceiver(), new IntentFilter("mock.crash.network"),
                "com.supets.pet.permission.MOCK_CRASH_NETWORK", null);
        registerReceiver(new CrashService(), new IntentFilter("mock.crash.service"),
                "com.supets.pet.permission.MOCK_CRASH_SERVICE", null);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (server != null) {
            server.stop();
        }
        if (mAsyncServer != null) {
            mAsyncServer.stop();
        }
    }
}
