package com.supets.pet.mocklib.mock;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class TuziMockManager {

    public static Interceptor getMockInterceptors() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request());
            }
        };
    }

    public static Interceptor getMockLogInterceptors() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request());
            }
        };
    }

}