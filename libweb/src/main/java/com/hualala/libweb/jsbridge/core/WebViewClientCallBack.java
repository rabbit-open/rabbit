package com.hualala.libweb.jsbridge.core;

import com.tencent.smtt.sdk.WebView;

public interface WebViewClientCallBack {
    //URL处理
    void onLoadHttp(String url);

    void onLoadOtherUri(String url);

    //页面加载状态
    void onPageStarted(WebView view, String url);

    void onPageFinished(WebView view, String url);

    void onReceivedError(WebView view, int errorCode);

    void onReceivedHttpError(WebView view, int statusCode);

}
