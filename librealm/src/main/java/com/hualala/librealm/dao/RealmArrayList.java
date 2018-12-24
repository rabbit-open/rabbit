package com.hualala.librealm.dao;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;


/**
 * @param <T> 类型
 * @ Intent序列化会转换成ArrayList
 *
 * <example>
 *
 * <p>     转换前
 * <p>     RealmArrayList<Dog> list = new RealmArrayList<>();
 * <p>     list.add(dog);
 * <p>     intent.putExtra("dogs", list);
 * <p>     转换后
 * <p>     ArrayList<Dog> dogss = (ArrayList<Dog>) getIntent().getSerializableExtra("dogs");
 * <p>     for (Dog dogs : dogss) {
 * <p>          Log.v("dogs:", dogs.name + dogs.age);
 * <p>          new DogDao().addDog(dogs);
 * <p>     }
 *
 * </example>
 */

public class RealmArrayList<T extends RealmModel> extends RealmList<T> implements Serializable {

}
