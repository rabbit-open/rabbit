package com.hualala.libweb;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.hualala.libweb.jsbridge.core.BaseWebChromeClient;
import com.hualala.libweb.jsbridge.core.BaseWebViewClient;
import com.hualala.libweb.jsbridge.core.ChromeClientCallBack;
import com.hualala.libweb.jsbridge.core.LogUtils;
import com.hualala.libweb.jsbridge.core.WebViewClientCallBack;
import com.hualala.libweb.jsbridge.core.X5WebView;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebView;

public class WebViewActivity extends Activity implements WebViewClientCallBack, ChromeClientCallBack {

    public static final String TAG = "[orderActivity]";

    public static final String KEY_URL = "key_url";

    private X5WebView mWebView;

    private boolean isError = false;

    private Button mTitle;
    private ProgressBar mProgressBar;
    private Button mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);


        mBack = findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getWebView().canGoBack()) {
                    getWebView().goBack();
                } else {
                    finish();
                }
            }

        });
        mTitle = findViewById(R.id.title);
        mProgressBar = findViewById(R.id.progress);

        mWebView = findViewById(R.id.webview);
        getWebView().setWebViewClient(new BaseWebViewClient(this));
        getWebView().setWebChromeClient(new BaseWebChromeClient(this));
        loadUrl();
    }

    private void onErrorFrensh() {
        getWebView().reload();
    }

    protected void loadUrl() {
        String mUrl = getUrl();
        if (mUrl != null) {
            LogUtils.d(TAG, mUrl);
            getWebView().loadUrl(mUrl);
        }
    }

    public String getUrl() {

        Uri uri = getIntent().getData();
        if (uri != null) {
            return uri.toString();
        }
        String mUrl = getIntent().getStringExtra(KEY_URL);
        if (mUrl == null) {
            mUrl = "";
        }
        return mUrl;
    }

    @Override
    public void onDestroy() {
        if (getWebView() != null) {
            getWebView().stopLoading();
            getWebView().destroy();
            removeCookie();
        }
        super.onDestroy();
    }

    private void removeCookie() {
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().removeAllCookie();
    }

    public WebView getWebView() {
        return mWebView;
    }

    @Override
    public void onLoadHttp(String url) {
        LogUtils.d(TAG, url);
        getWebView().loadUrl(url);
    }

    @Override
    public void onLoadOtherUri(String url) {
        LogUtils.d(TAG, url);
    }

    @Override
    public void onPageStarted(WebView view, String url) {

    }

    @Override
    public void onPageFinished(WebView view, String url) {

    }

    @Override
    public void onReceivedError(WebView view, int errorCode) {
        if (errorCode < 0) {

        }
    }

    @Override
    public void onReceivedHttpError(WebView view, int statusCode) {
        if (statusCode == 404 || statusCode == 500) {

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //再次进入刷新，解决服务器出现网关错误等信息
        if (isError) {
            onErrorFrensh();
        }
    }


    @Override
    public void onReceivedTitle(WebView view, String title) {
        mTitle.setText(title);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        mProgressBar.setProgress(newProgress);
        mProgressBar.setVisibility(newProgress == 100 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onCloseWindow(WebView view) {
        finish();
    }
}

