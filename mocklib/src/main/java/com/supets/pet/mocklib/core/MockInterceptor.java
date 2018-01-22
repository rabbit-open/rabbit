package com.supets.pet.mocklib.core;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MockInterceptor implements Interceptor {

    private IMockService mockMapper;

    public MockInterceptor() {
        this.mockMapper = new IMockServiceImpl()
                .addDataMapper(new MockDataMapper());
    }

    public MockInterceptor(IMockService mockMapper) {
        this.mockMapper = mockMapper;
        if (this.mockMapper == null) {
            this.mockMapper = new IMockServiceImpl()
                    .addDataMapper(new MockDataMapper());
        }
    }


    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = null;

        Response.Builder responsebuilder = new Response.Builder()
                .code(200)
                .message("success")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .addHeader("Content-Type", "application/x-www-form-urlencoded");
        Request request = chain.request();
        String url = request.url().toString();
        if (mockMapper.getMapper(url)) {
            String dto = mockMapper.getData(url);
            Log.v("MockMapper-->>", url + "\n" + dto);
            responsebuilder.body(ResponseBody.create(
                    MediaType.parse("application/json"),
                    dto.getBytes()));
            response = responsebuilder.build();
        } else {
            response = chain.proceed(request);
        }

        return response;
    }
}
