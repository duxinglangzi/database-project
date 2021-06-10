package com.conor.service.user.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: </p>
 * <p>@Author wuqiong  2021/6/10 </p>
 */
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1342623404222185586L;
    private Long id ;
    private String uniqueId;
    private String name;
    private String language;
    private Date registerDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
