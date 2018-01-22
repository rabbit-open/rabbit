package com.supets.pet.mock.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class LocalMockData {

    @Id
    private Long id;
    @Unique
    @Property(nameInDb = "url")
    private String url;
    @Property(nameInDb = "data")
    private String data;
    @Property(nameInDb = "selected")
    private Boolean selected;

    @Generated(hash = 1507613591)
    public LocalMockData(Long id, String url, String data, Boolean selected) {
        this.id = id;
        this.url = url;
        this.data = data;
        this.selected = selected;
    }
    @Generated(hash = 83650390)
    public LocalMockData() {
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public Boolean getSelected() {
        return this.selected;
    }
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

}
