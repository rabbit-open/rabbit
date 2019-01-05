package com.hualala.protof.service;

import com.hualala.proto.dto.LoginRequestV2;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers({"Content-Type:x-protobuf;charset=UTF-8"})
    @POST("login.action")
    Observable<LoginRequestV2.LoginResponse> login(@Body RequestBody bytes);
}
