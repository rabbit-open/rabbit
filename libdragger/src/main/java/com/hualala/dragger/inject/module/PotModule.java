package com.hualala.dragger.inject.module;

import com.hualala.dragger.inject.model.Flower;
import com.hualala.dragger.inject.model.Pot;
import com.hualala.dragger.inject.model.RoseFlower;

import dagger.Module;
import dagger.Provides;

@Module
public class PotModule {

    @Provides
    Pot providePot(@RoseFlower Flower flower) {
        return new Pot(flower);
    }
}