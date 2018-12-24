package com.hualala.librealm.model;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Dog extends RealmObject implements Serializable, RealmModel {
    @PrimaryKey
    public String name;
    public int age;
    public RealmList<DogEmail> emails;
}