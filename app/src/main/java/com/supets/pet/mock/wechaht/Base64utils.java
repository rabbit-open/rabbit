package com.supets.pet.mock.wechaht;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Base64;

/**
 * Created by thinkpad on 2018/3/25.
 */

public class Base64utils {

    /**
     * Base64加密
     *
     * @param str
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encode(String str) {
        byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes());
        return new String(encodeBytes);
    }

    /**
     * Base64解密
     *
     * @param str
     * @return
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decode(String str) {
        byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes());
        return new String(decodeBytes);
    }
}
