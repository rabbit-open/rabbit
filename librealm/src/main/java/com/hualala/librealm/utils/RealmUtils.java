package com.hualala.librealm.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * RealmList 不能序列化
 * Intent传递可以使用json互转
 */
public class RealmUtils {

    /**
     * @param data 集合
     * @param <T>  类型
     * @return json
     * @ <example>
     *
     * <p>    RealmList<Dog> list = new RealmList<>();
     * <p>    list.add(dog);
     * <p>    intent.putExtra("dog2", new Gson().toJson(list));
     *
     * </example>
     */
    public static <T> String realmListToJson(RealmList<T> data) {
        return new Gson().toJson(data);
    }

    public static <T> String realmToJson(RealmObject data) {
        return new Gson().toJson(data);
    }

    /**
     * @param json json
     * @param <T>  泛型
     * @return 集合
     * @ <example>
     * <p>
     * <P/>     String dog2 = getIntent().getStringExtra("dog2");
     * <P/>     RealmList<Dog> dog2s = new Gson().fromJson(dog2, new TypeToken<RealmList<Dog>>() {
     * <P/>     }.getType());
     *
     * <example>
     */
    public static <T> RealmList<T> jsonToRealmList(String json) {
        return new Gson().fromJson(json, new TypeToken<RealmList<T>>() {
        }.getType());
    }

    public static <T> T jsonToRealm(String json, Class<T> type) {
        return new Gson().fromJson(json, type);
    }
}
