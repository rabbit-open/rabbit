package com.supets.pet.mock.wechaht;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    public static void main(String[] args) throws Exception {
        // 设置加密密匙
        String masterPassword = "AndroidERHJ";
        // 设置原文
        String originalText = "ABCDEFGHIJKLMNOPQRS547KYU,N;[HTUVWXYZ";
        // 调用AES加密方法
        String encryptingCode = AES.encrypt(masterPassword, originalText);
        System.out.println("加密结果：" + encryptingCode);
        // 调用AES解密方法
        String decryString = AES.decrypt(masterPassword, encryptingCode);
        System.out.println("解密结果：" + decryString);
    }

    // AES加密
    // encryptText为要加密的内容
    // seed为密匙
    public static String encrypt(String seed, String encryptText)
            throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] result = encrypt(rawKey, encryptText.getBytes());
        return toHex(result);
    }

    // AES解密
    // decryptText为需要解密的内容
    // seed为密匙
    public static String decrypt(String seed, String decryptText)
            throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] enc = toByte(decryptText);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);

    }

    // 加密
    private static byte[] encrypt(byte[] raw, byte[] bytes) throws Exception {
        // 生成一组扩展密匙，并放入一个数组之中
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        // 用ENCRYPT_MODE模式,用skeySpec密码组，生成AES加密方法
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        // 得到加密数据
        byte[] encrypted = cipher.doFinal(bytes);
        return encrypted;
    }

    // 解密
    private static byte[] decrypt(byte[] rawKey, byte[] enc) throws Exception {
        // 生成一组扩展密匙，并放入一个数组之中
        SecretKeySpec skeyKeySpec = new SecretKeySpec(rawKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        // 用DECRYPT_MODE模式,用skeySpec密码组，生成AES解密方法
        cipher.init(Cipher.DECRYPT_MODE, skeyKeySpec);
        // 得到解密数据
        byte[] decrypted = cipher.doFinal(enc);
        return decrypted;
    }

    // 对密匙进行编码
    private static byte[] getRawKey(byte[] bytes) throws Exception {
        // 获取密匙生成器
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(bytes);
        // 生成128位的AES密码生成器
        kgen.init(128, sr);
        // 生成密匙
        SecretKey sKey = kgen.generateKey();
        // 编码格式
        byte[] raw = sKey.getEncoded();
        return raw;
    }

    // 将十六进制字符串为十进制字符串
    private static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    // 将十六进制字符串为十进制字节数组
    private static byte[] toByte(String hex) {
        int len = hex.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hex.substring(2 * i, 2 * i + 2), 16)
                    .byteValue();
        }
        return result;
    }

    // 把一个十进制字节数组转换成十六进制
    private static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    // 把一个十进制字节数组转换成十六进制
    private static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private final static String HEX = "0123456789ABCDEF";

    private static void appendHex(StringBuffer result, byte b) {
        result.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0X0f));

    }

}