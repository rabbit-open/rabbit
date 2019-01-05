package com.hualala.dragger.inject.module;

import com.hualala.dragger.inject.model.Flower;
import com.hualala.dragger.inject.model.Lily;
import com.hualala.dragger.inject.model.LilyFlower;
import com.hualala.dragger.inject.model.Rose;
import com.hualala.dragger.inject.model.RoseFlower;

import dagger.Module;
import dagger.Provides;

@Module
public class FlowerModule {
 
    @Provides
    @RoseFlower
    Flower provideRose() {
        return new Rose();
    }
 
    @Provides
    @LilyFlower
    Flower provideLily() {
        return new Lily();
    }
}