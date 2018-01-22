package com.supets.pet.mock.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EmailData {
    @Id
    private Long id;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "email")
    private String email;
    @Generated(hash = 1411676730)
    public EmailData(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    @Generated(hash = 228365985)
    public EmailData() {
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
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
