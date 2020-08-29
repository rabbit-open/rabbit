package com.supets.pet.mock.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

@Entity
public class MockData {

    @Id
    private Long id;
    @Property(nameInDb = "url")
    private String url;
    @Property(nameInDb = "requestParam")
    private String requestParam;
    @Property(nameInDb = "headerParam")
    private String headerParam;
    @Property(nameInDb = "data")
    private String data;
    @Property(nameInDb = "time")
    private Date  time;

    @Generated(hash = 677152694)
    public MockData(Long id, String url, String requestParam, String headerParam,
            String data, Date time) {
        this.id = id;
        this.url = url;
        this.requestParam = requestParam;
        this.headerParam = headerParam;
        this.data = data;
        this.time = time;
    }

    @Generated(hash = 2013467127)
    public MockData() {
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

    public String getRequestParam() {
        return this.requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getHeaderParam() {
        return this.headerParam;
    }

    public void setHeaderParam(String headerParam) {
        this.headerParam = headerParam;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
