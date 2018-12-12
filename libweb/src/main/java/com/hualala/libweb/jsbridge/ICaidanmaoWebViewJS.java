package com.hualala.libweb.jsbridge;


import com.tencent.smtt.sdk.WebView;

public interface ICaidanmaoWebViewJS {

    //语音识别结果传递
    void inputByVoice(WebView webView, String json);

}
