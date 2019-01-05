package com.hualala.protof;

import android.content.Context;

import com.hualala.proto.dto.LoginRequestV2;
import com.hualala.protof.service.ApiService;
import com.hualala.protof.service.HttpConfiger;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 网络发送器
 * Created by HDL on 2018/8/13.
 *
 * @author HDL
 */
public class ApiServiceImpl {
    private static ApiServiceImpl mHttpSend;
    private ApiService apiService;

    private ApiServiceImpl() {
    }

    public static ApiServiceImpl getInstance() {
        if (mHttpSend == null) {
            synchronized (ApiServiceImpl.class) {
                if (mHttpSend == null) {
                    mHttpSend = new ApiServiceImpl();
                }
            }
        }
        return mHttpSend;
    }

    /**
     * 初始化上下文对象
     *
     * @param context
     */
    public void initContext(Context context) {
        HttpConfiger.getInstance().initContext(context.getApplicationContext());
        apiService = HttpConfiger.getInstance().getRetrofit().create(ApiService.class);
    }

    /**
     * 用户登陆
     *
     * @param username   用户名
     * @param pwd        密码
     * @param subscriber
     */
    public void login(String username, String pwd, ResultCallbackListener<LoginRequestV2.LoginResponse> subscriber) { //构建请求信息
        LoginRequestV2.LoginRequest loginRequest = LoginRequestV2.LoginRequest.newBuilder().setUsername(username).setPwd(pwd).build(); //将loginrequest作为流（body）的形式
        RequestBody parms = RequestBody.create(MediaType.parse("application/octet-stream"), loginRequest.toByteArray());
        Observable<LoginRequestV2.LoginResponse> login = apiService.login(parms);
        HttpConfiger.getInstance().toSubscribe(login).subscribe(subscriber);
    }
}
