package com.supets.pet.mock.wechaht;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtils {
    /**
     * 序列化对象
     *
     * @param
     * @return
     * @throws IOException
     */
    public static <T> String serialize(T seria) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(seria);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serStr;
    }

    /**
     * 反序列化对象
     *
     * @param <T>
     * @param str
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static <T> T deSerialization(String str, T seria) throws IOException,
            ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        seria = (T) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return seria;
    }

    public static <T> T deSerialization(byte[] str, T seria) throws IOException,
            ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                str);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        seria = (T) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return seria;
    }
}
