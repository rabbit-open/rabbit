package com.hualala.libweb.jsbridge.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Process;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebView;

public class X5WebView extends WebView {

    public X5WebView(Context context) {
        super(context);
        initSetting();
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initSetting();
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initSetting();
    }

    public void initSetting() {
        String userAgent = getSettings().getUserAgentString();
        getSettings().setUserAgentString(userAgent + "/CaidanmaoMT:V1.1.0");
        //mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        getSettings().setDefaultTextEncodingName("UTF-8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        getSettings().setJavaScriptEnabled(true);
        getSettings().setDomStorageEnabled(true);
        getSettings().setSupportZoom(false);
        getSettings().setBuiltInZoomControls(false);
        getSettings().setUseWideViewPort(false);
        getSettings().setAllowFileAccess(true);
        getSettings().setAppCacheEnabled(true);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        getSettings().setGeolocationEnabled(true);
    }


    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean ret = super.drawChild(canvas, child, drawingTime);
        canvas.save();
        Paint paint = new Paint();
        paint.setColor(0x7fff0000);
        paint.setTextSize(24.f);
        paint.setAntiAlias(true);
        if (getX5WebViewExtension() != null) {
            canvas.drawText(this.getContext().getPackageName() + "-pid:"
                    + Process.myPid(), 10, 50, paint);
            canvas.drawText(
                    "X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10,
                    100, paint);
        } else {
            canvas.drawText(this.getContext().getPackageName() + "-pid:"
                    + Process.myPid(), 10, 50, paint);
            canvas.drawText("Sys Core", 10, 100, paint);
        }
        canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
        canvas.drawText(Build.MODEL, 10, 200, paint);
        canvas.restore();
        return ret;
    }

}
