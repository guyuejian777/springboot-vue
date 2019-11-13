package com.dajian.vhrserver.pojo;

import java.util.Date;

public class Position {
    private Long id; //int(11) NOT NULL AUTO_INCREMENT,
    private String name; //varchar(32) DEFAULT NULL COMMENT '职位',
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
