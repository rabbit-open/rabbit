package com.supets.pet.mocklib.core;


import com.supets.pet.mocklib.MockConfig;

import java.util.ArrayList;
import java.util.List;

public class IMockServiceImpl implements IMockService {

    private    List<IMockDataMapper> maps=new ArrayList<>();

    public IMockServiceImpl() {

    }

    @Override
    public boolean getMapper(String url) {
        if (!MockConfig.getDebugMode()){
            return false;
        }
        for (IMockDataMapper mapper:maps){
            if (mapper.getMapper(url)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String getData(String url) {
        for (IMockDataMapper mapper:maps){
            if (mapper.getMapper(url)){
                return mapper.getData(url);
            }
        }
        return  null;
    }

    public IMockServiceImpl addDataMapper(IMockDataMapper mapper){
        maps.add(mapper);
        return this;
    }

}
