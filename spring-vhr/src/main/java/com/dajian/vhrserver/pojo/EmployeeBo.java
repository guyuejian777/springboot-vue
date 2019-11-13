package com.dajian.vhrserver.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;
public class EmployeeBo extends Employee {
    private String nationName;
    private String politicName;
    private String departmentName;
    private String jobLevelName;
    private String posName;

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public String getPoliticName() {
        return politicName;
    }

    public void setPoliticName(String politicName) {
        this.politicName = politicName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJobLevelName() {
        return jobLevelName;
    }

    public void setJobLevelName(String jobLevelName) {
        this.jobLevelName = jobLevelName;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
