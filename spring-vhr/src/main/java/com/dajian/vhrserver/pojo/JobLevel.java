package com.dajian.vhrserver.pojo;

import java.util.Date;

public class JobLevel {
    private Long id; //int(11) NOT NULL AUTO_INCREMENT,
    private String name; //varchar(32) DEFAULT NULL COMMENT '职称名称',
    private String titleLevel; //enum('正高级','副高级','中级','初级','员级') DEFAULT NULL,
    private Date createDate; //timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    private Boolean enabled; //tinyint(1) DEFAULT '1',

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitleLevel() {
        return titleLevel;
    }

    public void setTitleLevel(String titleLevel) {
        this.titleLevel = titleLevel;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
