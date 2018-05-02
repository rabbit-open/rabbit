package com.supets.pet.mock.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class WordData {
    @Id
    private Long id;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "module")
    private String module;
    @Generated(hash = 1940970128)
    public WordData(Long id, String name, String module) {
        this.id = id;
        this.name = name;
        this.module = module;
    }
    @Generated(hash = 551690561)
    public WordData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getModule() {
        return this.module;
    }
    public void setModule(String module) {
        this.module = module;
    }


}
