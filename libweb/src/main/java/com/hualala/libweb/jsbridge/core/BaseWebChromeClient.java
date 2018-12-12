package com.hualala.libweb.jsbridge.core;


import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

public class BaseWebChromeClient extends WebChromeClient {

    private static final String TAG = "[BaseWebChromeClient]";

    private ChromeClientCallBack mListener;

    public BaseWebChromeClient(ChromeClientCallBack mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        LogUtils.d(TAG, "onReceivedTitle:" + title);
        if (mListener != null) {
            mListener.onReceivedTitle(view, title);
        }
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        LogUtils.d(TAG, "onProgressChanged:" + newProgress);
        if (mListener != null) {
            mListener.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public void onCloseWindow(WebView view) {
        LogUtils.d(TAG, "onCloseWindow");
        if (mListener != null) {
            mListener.onCloseWindow(view);
        }
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        LogUtils.d(TAG, consoleMessage.message() + " -- From line " + consoleMessage.lineNumber() + " of " + consoleMessage.sourceId());
        return super.onConsoleMessage(consoleMessage);
    }

}