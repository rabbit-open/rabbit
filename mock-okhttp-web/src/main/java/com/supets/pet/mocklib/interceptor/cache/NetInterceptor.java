package com.supets.pet.mocklib.interceptor.cache;

import com.supets.pet.AppContext;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        boolean connected = NetUtil.isNetworkConnected();
        if (connected) {
            Response response = chain.proceed(request);
            int maxTime = 90;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxTime)
                    .build();
        }
        return chain.proceed(request);
    }


    public static void main(String[] args) {
        File file = new File(AppContext.INSTANCE.getCacheDir(), "rxCache");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(file, cacheSize);
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new NoNetInterceptor())
                .addNetworkInterceptor(new NetInterceptor())
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

    }
}
