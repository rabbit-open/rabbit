package com.hualala.dragger.inject.model;

import javax.inject.Inject;

public class Pot {

    private Flower flower;

    @Inject
    public Pot(@RoseFlower Flower flower) {
        this.flower = flower;
    }

    public String show() {
        return flower.whisper();
    }
}