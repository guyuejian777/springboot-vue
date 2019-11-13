package com.dajian.vhrserver.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Employee {
    private Long id; //int(11) NOT NULL AUTO_INCREMENT COMMENT '员工编号',
    private String name; //varchar(10) DEFAULT NULL COMMENT '员工姓名',
    private String gender; //char(4) DEFAULT NULL COMMENT '性别',
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday; //date DEFAULT NULL COMMENT '出生日期',
    private String idCard; //char(18) DEFAULT NULL COMMENT '身份证号',
    private String wedlock; //enum('已婚','未婚','离异') DEFAULT NULL COMMENT '婚姻状况',
    private Long nationId; //int(8) DEFAULT NULL COMMENT '民族',
    private String nativePlace; //varchar(20) DEFAULT NULL COMMENT '籍贯',
    private Long politicId; //int(8) DEFAULT NULL COMMENT '政治面貌',
    private String email; //varchar(20) DEFAULT NULL COMMENT '邮箱',
    private String phone; //varchar(11) DEFAULT NULL COMMENT '电话号码',
    private String address; //varchar(64) DEFAULT NULL COMMENT '联系地址',
    private Long departmentId; //int(11) DEFAULT NULL COMMENT '所属部门',
    private Long jobLevelId; //int(11) DEFAULT NULL COMMENT '职称ID',
    private Long posId; //int(11) DEFAULT NULL COMMENT '职位ID',
    private String engageForm; //varchar(8) DEFAULT NULL COMMENT '聘用形式',
    private String tiptopDegree; //enum('博士','硕士','本科','大专','高中','初中','小学','其他') DEFAULT NULL COMMENT '最高学历',
    private String specialty; //varchar(32) DEFAULT NULL COMMENT '所属专业',
    private String school; //varchar(32) DEFAULT NULL COMMENT '毕业院校',
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginDate; //date DEFAULT NULL COMMENT '入职日期',
    private String workState; //enum('在职','离职') DEFAULT '在职' COMMENT '在职状态',
    private String workID; //char(8) DEFAULT NULL COMMENT '工号',
    private Double contractTerm; //double DEFAULT NULL COMMENT '合同期限',
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date conversionTime; //date DEFAULT NULL COMMENT '转正日期',
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date notWorkDate; //date DEFAULT NULL COMMENT '离职日期',
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginContract; //date DEFAULT NULL COMMENT '合同起始日期',
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endContract; //date DEFAULT NULL COMMENT '合同终止日期',
    private Integer workAge; //int(11) DEFAULT NULL COMMENT '工龄',
    private Salary salary;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", idCard='" + idCard + '\'' +
                ", wedlock='" + wedlock + '\'' +
                ", nationId=" + nationId +
                ", nativePlace='" + nativePlace + '\'' +
                ", politicId=" + politicId +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", departmentId=" + departmentId +
                ", jobLevelId=" + jobLevelId +
                ", posId=" + posId +
                ", engageForm='" + engageForm + '\'' +
                ", tiptopDegree='" + tiptopDegree + '\'' +
                ", specialty='" + specialty + '\'' +
                ", school='" + school + '\'' +
                ", beginDate=" + beginDate +
                ", workState='" + workState + '\'' +
                ", workID='" + workID + '\'' +
                ", contractTerm=" + contractTerm +
                ", conversionTime=" + conversionTime +
                ", notWorkDate=" + notWorkDate +
                ", beginContract=" + beginContract +
                ", endContract=" + endContract +
                ", workAge=" + workAge +
                ", salary=" + salary +
                '}';
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getWedlock() {
        return wedlock;
    }

    public void setWedlock(String wedlock) {
        this.wedlock = wedlock;
    }

    public Long getNationId() {
        return nationId;
    }

    public void setNationId(Long nationId) {
        this.nationId = nationId;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Long getPoliticId() {
        return politicId;
    }

    public void setPoliticId(Long politicId) {
        this.politicId = politicId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getJobLevelId() {
        return jobLevelId;
    }

    public void setJobLevelId(Long jobLevelId) {
        this.jobLevelId = jobLevelId;
    }

    public Long getPosId() {
        return posId;
    }

    public void setPosId(Long posId) {
        this.posId = posId;
    }

    public String getEngageForm() {
        return engageForm;
    }

    public void setEngageForm(String engageForm) {
        this.engageForm = engageForm;
    }

    public String getTiptopDegree() {
        return tiptopDegree;
    }

    public void setTiptopDegree(String tiptopDegree) {
        this.tiptopDegree = tiptopDegree;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public String getWorkState() {
        return workState;
    }

    public void setWorkState(String workState) {
        this.workState = workState;
    }

    public String getWorkID() {
        return workID;
    }

    public void setWorkID(String workID) {
        this.workID = workID;
    }

    public Double getContractTerm() {
        return contractTerm;
    }

    public void setContractTerm(Double contractTerm) {
        this.contractTerm = contractTerm;
    }

    public Date getConversionTime() {
        return conversionTime;
    }

    public void setConversionTime(Date conversionTime) {
        this.conversionTime = conversionTime;
    }

    public Date getNotWorkDate() {
        return notWorkDate;
    }

    public void setNotWorkDate(Date notWorkDate) {
        this.notWorkDate = notWorkDate;
    }

    public Date getBeginContract() {
        return beginContract;
    }

    public void setBeginContract(Date beginContract) {
        this.beginContract = beginContract;
    }

    public Date getEndContract() {
        return endContract;
    }

    public void setEndContract(Date endContract) {
        this.endContract = endContract;
    }

    public Integer getWorkAge() {
        return workAge;
    }

    public void setWorkAge(Integer workAge) {
        this.workAge = workAge;
    }
}
