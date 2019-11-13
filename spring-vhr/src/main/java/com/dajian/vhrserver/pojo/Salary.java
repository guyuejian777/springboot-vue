package com.dajian.vhrserver.pojo;

import java.util.Date;

public class Salary {
    private Long id; //int(11) NOT NULL AUTO_INCREMENT,
    private Integer basicSalary; //int(11) DEFAULT NULL COMMENT '基本工资',
    private Integer bonus; //int(11) DEFAULT NULL COMMENT '奖金',
    private Integer lunchSalary; //int(11) DEFAULT NULL COMMENT '午餐补助',
    private Integer trafficSalary; //int(11) DEFAULT NULL COMMENT '交通补助',
    private Integer allSalary; //int(11) DEFAULT NULL COMMENT '应发工资',
    private Integer pensionBase; //int(11) DEFAULT NULL COMMENT '养老金基数',
    private Float pensionPer; //float DEFAULT NULL COMMENT '养老金比率',
    private Date createDate; //timestamp NULL DEFAULT NULL COMMENT '启用时间',
    private Integer medicalBase; //int(11) DEFAULT NULL COMMENT '医疗基数',
    private Float medicalPer; //float DEFAULT NULL COMMENT '医疗保险比率',
    private Integer accumulationFundBase; //int(11) DEFAULT NULL COMMENT '公积金基数',
    private Float accumulationFundPer; //float DEFAULT NULL COMMENT '公积金比率',
    private String name; //varchar(32) DEFAULT NULL,

    @Override
    public String toString() {
        return "Salary{" +
                "id=" + id +
                ", basicSalary=" + basicSalary +
                ", bonus=" + bonus +
                ", lunchSalary=" + lunchSalary +
                ", trafficSalary=" + trafficSalary +
                ", allSalary=" + allSalary +
                ", pensionBase=" + pensionBase +
                ", pensionPer=" + pensionPer +
                ", createDate=" + createDate +
                ", medicalBase=" + medicalBase +
                ", medicalPer=" + medicalPer +
                ", accumulationFundBase=" + accumulationFundBase +
                ", accumulationFundPer=" + accumulationFundPer +
                ", name='" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Integer basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Integer getLunchSalary() {
        return lunchSalary;
    }

    public void setLunchSalary(Integer lunchSalary) {
        this.lunchSalary = lunchSalary;
    }

    public Integer getTrafficSalary() {
        return trafficSalary;
    }

    public void setTrafficSalary(Integer trafficSalary) {
        this.trafficSalary = trafficSalary;
    }

    public Integer getAllSalary() {
        return allSalary;
    }

    public void setAllSalary(Integer allSalary) {
        this.allSalary = allSalary;
    }

    public Integer getPensionBase() {
        return pensionBase;
    }

    public void setPensionBase(Integer pensionBase) {
        this.pensionBase = pensionBase;
    }

    public Float getPensionPer() {
        return pensionPer;
    }

    public void setPensionPer(Float pensionPer) {
        this.pensionPer = pensionPer;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getMedicalBase() {
        return medicalBase;
    }

    public void setMedicalBase(Integer medicalBase) {
        this.medicalBase = medicalBase;
    }

    public Float getMedicalPer() {
        return medicalPer;
    }

    public void setMedicalPer(Float medicalPer) {
        this.medicalPer = medicalPer;
    }

    public Integer getAccumulationFundBase() {
        return accumulationFundBase;
    }

    public void setAccumulationFundBase(Integer accumulationFundBase) {
        this.accumulationFundBase = accumulationFundBase;
    }

    public Float getAccumulationFundPer() {
        return accumulationFundPer;
    }

    public void setAccumulationFundPer(Float accumulationFundPer) {
        this.accumulationFundPer = accumulationFundPer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
