package com.hualala.libweb.jsbridge.core;


import com.tencent.smtt.sdk.WebView;

public interface ChromeClientCallBack {

    void onReceivedTitle(WebView view, String title);

    void onProgressChanged(WebView view, int newProgress);

    void onCloseWindow(WebView view);
}
