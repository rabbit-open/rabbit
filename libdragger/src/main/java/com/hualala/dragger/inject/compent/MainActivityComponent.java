package com.hualala.dragger.inject.compent;

import com.hualala.dragger.MainActivity;
import com.hualala.dragger.inject.module.FlowerModule;
import com.hualala.dragger.inject.module.PotModule;

import dagger.Component;

@Component(modules = {FlowerModule.class, PotModule.class})
public interface MainActivityComponent {
    void inject(MainActivity activity);
}