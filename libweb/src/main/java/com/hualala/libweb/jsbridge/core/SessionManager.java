package com.hualala.libweb.jsbridge.core;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {

    public static Map<String, String> addUserArgs(String url) {
        if (url == null) {
            return removeUserArgs();
        }
        if (url.startsWith("http")) {
            Map<String, String> exHeader = new HashMap<>();
            return exHeader;
        }
        return removeUserArgs();
    }


    private static Map<String, String> removeUserArgs() {
        Map<String, String> exHeader = new HashMap<>();
        return exHeader;
    }

}
