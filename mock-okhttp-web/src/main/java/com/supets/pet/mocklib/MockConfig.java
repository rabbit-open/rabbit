package com.supets.pet.mocklib;

import android.os.Environment;

import com.supets.pet.AppContext;

public class MockConfig {

    public static final String permission_mock_network = AppContext.INSTANCE.getPackageName() + ".permission.MOCK_CRASH_NETWORK";
    public static final String RESPONSE_PATH = Environment.getExternalStorageDirectory() + "/兔子测试/response/cache/";
    public static final String RESPONSE_SUCCESS_PATH = Environment.getExternalStorageDirectory() + "/兔子测试/response/success/";
    public static final String RESPONSE_ERROR_PATH = Environment.getExternalStorageDirectory() + "/兔子测试/response/error/";
    public static final String MOCK_SERVICE_NETWORK = AppContext.INSTANCE.getPackageName() + ".mock.crash.network";
    public static final String MOCK_PROVIDE_AUTHORITY = AppContext.INSTANCE.getPackageName() + ".mockprovider";

    public static boolean getDebugSwitch() {
        return true;
    }

}
