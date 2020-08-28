package com.supets.pet.mocklib;

import java.util.HashMap;

public class MockConfig {

    public static final String permission_mock_network = "com.supets.pet.permission.MOCK_CRASH_NETWORK";
    public static final String permission_mock_crash = "com.supets.pet.permission.MOCK_CRASH_SERVICE";

    public static HashMap<String, String> bigMessage = new HashMap<>();
    public static HashMap<String, String> bigRequest = new HashMap<>();


    public static boolean getDebugSwitch() {
        return true;
    }

}
