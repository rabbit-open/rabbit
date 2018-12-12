package com.hualala.libweb.jsbridge.core;

import android.annotation.TargetApi;
import android.net.Uri;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;

public class BaseWebViewClient extends com.tencent.smtt.sdk.WebViewClient {

    private static final String TAG = "[BaseWebViewClient]";

    private WebViewClientCallBack mListener;

    public BaseWebViewClient(WebViewClientCallBack mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        LogUtils.d(TAG, "onPageStarted" + url);
        if (mListener != null) {
            mListener.onPageStarted(view, url);
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //绕过SSL
        handler.proceed();
        LogUtils.d(TAG, "onReceivedSslError");
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        LogUtils.d(TAG, "onPageFinished" + url);
        if (mListener != null) {
            mListener.onPageFinished(view, url);
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        LogUtils.d(TAG, "onReceivedError:errorCode" + errorCode + "-description:" + description + "-failingUrl:" + failingUrl);
        if (mListener != null) {
            mListener.onReceivedError(view, errorCode);
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return shouldOverrideUrlLoading(url, mListener);
    }

    @TargetApi(android.os.Build.VERSION_CODES.M)
    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        if (errorResponse != null) {
            LogUtils.d(TAG, "onReceivedHttpError-" + errorResponse.getStatusCode());
        }
        if (request.isForMainFrame()) {
            int statusCode = 0;
            if (errorResponse != null) {
                statusCode = errorResponse.getStatusCode();
            }
            if (mListener != null) {
                mListener.onReceivedHttpError(view, statusCode);
            }
        }
    }


    public static boolean shouldOverrideUrlLoading(String url, WebViewClientCallBack callBack) {
        LogUtils.d(TAG, url);
        if (url == null || "about:blank".equals(url)) {
            return true;
        }

        Uri uri = Uri.parse(url);

        if (isHttp(uri)) {
            if (callBack != null) {
                callBack.onLoadHttp(url);
            }
        } else {
            if (callBack != null) {
                callBack.onLoadOtherUri(url);
            }
        }
        return true;
    }

    private static boolean isHttp(Uri uri) {
        if (uri != null) {
            String scheme = uri.getScheme();
            if (scheme != null && scheme.startsWith("http")) {
                return true;
            }
        }
        return false;
    }

}
