package com.supets.pet.mocklib.interceptor.cache;

import android.util.Log;

import com.supets.pet.mocklib.AppContext;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 在有网络的情况下，先去读缓存，设置的缓存时间到了，在去网络获取
 */

public class NetInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        boolean connected = NetUtil.isNetworkConnected();
        if (connected) {
            //如果有网络，缓存90s
            Log.e("zhanghe", "print");
            Response response = chain.proceed(request);
            int maxTime = 90;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxTime)
                    .build();
        }
        //如果没有网络，不做处理，直接返回
        return chain.proceed(request);
    }


    public static void main(String[] args) {
        File file = new File(AppContext.INSTANCE.getCacheDir(), "rxCache");
        //缓存大小10M
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(file, cacheSize);
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)                        //设置缓存
                .addInterceptor(new NoNetInterceptor())    //将无网络拦截器当做应用拦截器添加
                .addNetworkInterceptor(new NetInterceptor())//将有网络拦截器当做网络拦截器添加
                .connectTimeout(5, TimeUnit.SECONDS) //连接超时
                .writeTimeout(5, TimeUnit.SECONDS)   //写入超时
                .readTimeout(5, TimeUnit.SECONDS)  //读取超时
                .build();

    }
}
