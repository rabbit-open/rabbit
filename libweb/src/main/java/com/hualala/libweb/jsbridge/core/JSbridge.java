package com.hualala.libweb.jsbridge.core;

import com.hualala.libweb.jsbridge.ICaidanmaoWebViewJS;
import com.tencent.smtt.sdk.WebView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class JSbridge implements ICaidanmaoWebViewJS {

    @Override
    public void inputByVoice(WebView webView, String json) {
        asyncExcuteJsMethed(webView, "inputByVoice", json);
    }

    private static void asyncExcuteJsMethed(WebView webView, String jsMethedName, String json) {
        String js = String.format(Locale.US, "javascript:" + jsMethedName + "('%s')", json);
        webView.loadUrl(js);
    }

    public static String addUrlArgs(String url, Map<String, String> params) {
        if (url == null || params == null) {
            return url;
        }

        if (!url.startsWith("http")) {
            return url;
        }

        try {
            StringBuilder sb = new StringBuilder(url);
            sb.append(url.contains("?") ? (url.endsWith("&") || url.endsWith("?") ? "" : "&") : "?");

            Iterator<String> keys = params.keySet().iterator();
            int count = 0;
            while (keys.hasNext()) {
                String key = keys.next();
                String vaule = params.get(key);
                if (vaule == null) {
                    continue;
                }
                String encodeVaule = URLEncoder.encode(vaule, "UTF-8");
                if (count == 0) {
                    sb.append(key.concat("=")).append(encodeVaule);
                } else {
                    sb.append("&".concat(key.concat("="))).append(encodeVaule);
                }
                count++;
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}
