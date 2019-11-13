package com.dajian.vhrserver.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {
    private Long id; //int(11) NOT NULL AUTO_INCREMENT,
    @JsonIgnore
    private String url; //varchar(64) DEFAULT NULL,
    private String path; //varchar(64) DEFAULT NULL,
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private Object component; //varchar(64) DEFAULT NULL,
    private String name; //varchar(64) DEFAULT NULL,
    private String iconCls; //varchar(64) DEFAULT NULL,
    @JsonIgnore
    private Long parentId; //int(11) DEFAULT NULL,

    @JsonIgnore
    private List<Role> roles;
    private List<Menu> children;
    private MenuMeta menuMeta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getComponent() {
        return component;
    }

    public void setComponent(Object component) {
        this.component = component;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public MenuMeta getMenuMeta() {
        return menuMeta;
    }

    public void setMenuMeta(MenuMeta menuMeta) {
        this.menuMeta = menuMeta;
    }
}
