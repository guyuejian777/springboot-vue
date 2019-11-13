package com.dajian.vhrserver.pojo;

import java.util.Date;

public class MsgContent {

    private Long id; //int(11) NOT NULL AUTO_INCREMENT,
    private String title; //varchar(64) DEFAULT NULL,
    private String message; //varchar(255) DEFAULT NULL,
    private Date createDate; //timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
