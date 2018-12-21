package com.supets.pet.mocklib.interceptor.cache;

import android.util.Log;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author: zh on 2017/4/13.
 * 在没有网络的情况下，取读缓存数据
 */

public class NoNetInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        boolean connected = NetUtil.isNetworkConnected();
        //如果没有网络，则启用 FORCE_CACHE
        if (!connected) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Log.e("zhanghe", "无网络设置_common");

            Response response = chain.proceed(request);
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=3600")
                    .removeHeader("Pragma")
                    .build();
        }
        //有网络的时候，这个拦截器不做处理，直接返回
        return chain.proceed(request);
    }
}
