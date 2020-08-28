package com.supets.pet.mocklib.mock;

import com.supets.pet.mocklib.core.MockInterceptor;

import okhttp3.Interceptor;

public class TuziMockManager {

    public static Interceptor getMockInterceptors() {
        return new MockInterceptor();
    }


    public static Interceptor getMockLogInterceptors() {
        return new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT,true);
    }

    public static void sendLocalRequest(String url, String requestParam, String message) {
        JsonFormatUtils.sendLocalRequest(url, requestParam, message);
    }
}