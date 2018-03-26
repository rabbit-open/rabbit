package com.supets.pet.mock.wechaht;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DES {
    // 初始化向量，随意填充
    private static byte[] iv = { 'a', 'b', 'c', 'd', 'e', 1, 2, '*' };

    public static void main(String[] args) {
        // 指定密匙
        String key = "*()&^%$#";
        // 指定需要加密的明文
        String text = "4454069u =o 5h6u= bopregkljoj";
        try {
            // 调用DES加密方法
            String encryString = DES.encryptDES(text, key);
            System.out.println("DES加密结果： " + encryString);
            // 调用DES解密方法
            String decryString = DES.decryptDES(encryString, key);
            System.out.println("DES解密结果： " + decryString);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // DES加密
    // encryptText为原文
    // encryptKey为密匙
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String encryptDES(String encryptText, String encryptKey)
            throws Exception {
        // 实例化IvParameterSpec对象，使用指定的初始化向量
        IvParameterSpec spec = new IvParameterSpec(iv);
        // 实例化SecretKeySpec类,根据字节数组来构造SecretKeySpec
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // 用密码初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        // 执行加密操作
        byte[] encryptData = cipher.doFinal(encryptText.getBytes());
        // 返回加密后的数据
        return Base64.getEncoder().encodeToString(encryptData);
    }

    // 解密
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String decryptDES(String decryptString, String decryptKey)
            throws Exception {
        // 先使用Base64解密
        byte[] base64byte = Base64.getDecoder().decode(decryptString);
        // 实例化IvParameterSpec对象，使用指定的初始化向量
        IvParameterSpec spec = new IvParameterSpec(iv);
        // 实例化SecretKeySpec类,根据字节数组来构造SecretKeySpec
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // 用密码初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        // 获取解密后的数据
        byte decryptedData[] = cipher.doFinal(base64byte);
        // 将解密后数据转换为字符串输出
        return new String(decryptedData);
    }

}