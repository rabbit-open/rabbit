package com.hualala.librealm.model;

import java.io.Serializable;

import io.realm.RealmObject;

public class DogEmail extends RealmObject implements Serializable {
    public String address;
}