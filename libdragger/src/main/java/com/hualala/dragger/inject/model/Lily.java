package com.hualala.dragger.inject.model;

import javax.inject.Inject;

public class Lily extends Flower {

    @Inject
    public Lily() {
    }

    @Override
    public String whisper() {
        return "纯洁";
    }
}