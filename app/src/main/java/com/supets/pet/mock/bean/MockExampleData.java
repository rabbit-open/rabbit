package com.supets.pet.mock.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class MockExampleData {

    @Id
    private Long id;

    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "data")
    private String data;

    @Generated(hash = 133887110)
    public MockExampleData(Long id, String name, String data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }
    @Generated(hash = 1716812212)
    public MockExampleData() {
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
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }

}
