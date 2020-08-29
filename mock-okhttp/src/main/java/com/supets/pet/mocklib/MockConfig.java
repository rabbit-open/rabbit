package com.supets.pet.mocklib;

import android.os.Environment;

public class MockConfig {

    public static final String permission_mock_network = "com.supets.pet.permission.MOCK_CRASH_NETWORK";
    public static final String permission_mock_crash = "com.supets.pet.permission.MOCK_CRASH_SERVICE";
    public static final String RESPONSE_PATH = Environment.getExternalStorageDirectory() + "/兔子测试/response/";

    public static boolean getDebugSwitch() {
        return true;
    }

}
