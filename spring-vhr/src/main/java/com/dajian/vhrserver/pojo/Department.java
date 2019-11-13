package com.dajian.vhrserver.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Department {
    private Long id; //int(11) NOT NULL AUTO_INCREMENT,
    private String name; //varchar(32) DEFAULT NULL COMMENT '部门名称',
    private Long parentId; //int(11) DEFAULT NULL,
    @JsonIgnore
    private String depPath; //varchar(255) DEFAULT NULL,
    private Boolean enabled; //tinyint(1) DEFAULT '1',
    private Boolean isParent; //tinyint(1) DEFAULT '0',
    private List<Department> children;

    public List<Department> getChildren() {
        return children;
    }

    public void setChildren(List<Department> children) {
        this.children = children;
    }

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDepPath() {
        return depPath;
    }

    public void setDepPath(String depPath) {
        this.depPath = depPath;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }
}
