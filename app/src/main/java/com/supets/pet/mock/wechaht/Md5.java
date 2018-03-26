package com.supets.pet.mock.wechaht;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密方法
 */
public class Md5 {

    /** Determine encrypt algorithm MD5 */
    private static final String ALGORITHM_MD5 = "MD5";
    /** UTF-8 Encoding */
    private static final String UTF_8 = "UTF-8";
    /**
     * 32位MD5加密方法
     * 16位小写加密只需getMd5Value("xxx").substring(8, 24);即可
     */
    public static String getMd5Value(String sSecret) {
        try {
            MessageDigest bmd5 = MessageDigest.getInstance("MD5");
            bmd5.update(sSecret.getBytes());
            int i;
            StringBuffer buf = new StringBuffer();
            byte[] b = bmd5.digest();// 加密
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) throws Exception {

        // chrome://net-internals/#events

        //获取微信数据库的密码
        String Imei = "###################";
        String uin = "########";

        String password = Md5.getMd5Value(Imei + uin).substring(0, 7);
        System.err.println(password);
    }
}