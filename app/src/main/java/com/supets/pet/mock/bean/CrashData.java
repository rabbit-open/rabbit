package com.supets.pet.mock.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CrashData {
    @Id
    private Long id;
    @Property(nameInDb = "crash")
    private String crash;
    @Generated(hash = 645541351)
    public CrashData(Long id, String crash) {
        this.id = id;
        this.crash = crash;
    }
    @Generated(hash = 252313082)
    public CrashData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCrash() {
        return this.crash;
    }
    public void setCrash(String crash) {
        this.crash = crash;
    }
}
