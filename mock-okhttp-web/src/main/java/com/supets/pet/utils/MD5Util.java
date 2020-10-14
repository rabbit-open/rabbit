package com.supets.pet.utils;



import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * Created by jiqixuexi on 2018/7/20.
 */

public class MD5Util {
    private static final String TAG = "MD5Util";

    public static String encode(String value) {
        String hs = "";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes());
            byte[] ctext = md.digest();
            String stmp = "";

            for (int n = 0; n < ctext.length; ++n) {
                stmp = Integer.toHexString(ctext[n] & 255);
                if (stmp.length() == 1) {
                    hs = hs + "0" + stmp;
                } else {
                    hs = hs + stmp;
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return hs;
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String md5 = bytesToHexString(digest.digest());
        return md5;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
