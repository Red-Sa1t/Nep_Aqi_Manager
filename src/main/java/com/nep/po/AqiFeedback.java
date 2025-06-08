package com.nep.po;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AqiFeedback implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("af_id")
    private Integer afId;

    @JsonProperty("tel_id")
    private String telId;

    @JsonProperty("province_id")
    private Integer provinceId;

    @JsonProperty("city_id")
    private Integer cityId;

    private String address;

    private String information;

    @JsonProperty("estimated_grade")
    private Integer estimatedGrade;

    @JsonProperty("af_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date afDate;

    @JsonProperty("af_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Date afTime;

    @JsonProperty("gm_id")
    private Integer gmId;
    private String gmName;

    @JsonProperty("assign_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String assignDate;

    @JsonProperty("assign_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Date assignTime;


    //todo: 没有把state，remarks和confirmDate合并进入AqiFeedBack构造函数
    private String state;

    private String remarks;

    private String confirmDate;	//ʵ������

    private Double so2;			//ʵ���������Ũ��
    private Double co;			//ʵ��һ����̼Ũ��
    private Double pm;			//ʵ��PM2.5Ũ��
    private String confirmLevel;//ʵ��AQI�ȼ�
    private String confirmExplain;//ʵ��AQI�ȼ�����

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    private String provinceName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    private String cityName;

    public String getAfName() {
        return afName;
    }

    public void setAfName(String afName) {
        this.afName = afName;
    }

    private String afName;	//���ڼලԱ����

    public AqiFeedback() {
    }

    public AqiFeedback(Integer afId, String telId, Integer provinceId, Integer cityId, String address, String information, Integer estimatedGrade, Date afDate, Date afTime, Integer gmId, String assignDate, Date assignTime, String state, String remarks, String gmName) {
        this.afId = afId;
        this.afDate = afDate;
        this.afTime = afTime;
        this.telId = telId;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.address = address;
        this.information = information;
        this.estimatedGrade = estimatedGrade;
        this.gmId = gmId;
        this.gmName = gmName;
        this.assignDate = assignDate;
        this.assignTime = assignTime;
        this.state = state;
        this.remarks = remarks;

    }

    public Integer getAfId() {
        return afId;
    }

    public void setAfId(Integer afId) {
        this.afId = afId;
    }

    public String getTelId() {
        return telId;
    }

    public void setTelId(String telId) {
        this.telId = telId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Integer getEstimatedGrade() {
        return estimatedGrade;
    }

    public void setEstimatedGrade(Integer estimatedGrade) {
        this.estimatedGrade = estimatedGrade;
    }

    public Date getAfDate() {
        return afDate;
    }

    public void setAfDate(Date afDate) {
        this.afDate = afDate;
    }

    public Date getAfTime() {
        return afTime;
    }

    public void setAfTime(Date afTime) {
        this.afTime = afTime;
    }

    public Integer getGmId() {
        return gmId;
    }

    public void setGmId(Integer gmId) {
        this.gmId = gmId;
    }

    public String getGmName() { return gmName; }

    public void setGmName(String gmName) { this.gmName=gmName; }

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Double getSo2() {
        return so2;
    }

    public void setSo2(Double so2) {
        this.so2 = so2;
    }


    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Double getCo() {
        return co;
    }

    public void setCo(Double co) {
        this.co = co;
    }

    public Double getPm() {
        return pm;
    }

    public void setPm(Double pm) {
        this.pm = pm;
    }

    public String getConfirmLevel() {
        return confirmLevel;
    }

    public void setConfirmLevel(String confirmLevel) {
        this.confirmLevel = confirmLevel;
    }

    public String getConfirmExplain() {
        return confirmExplain;
    }

    public void setConfirmExplain(String confirmExplain) {
        this.confirmExplain = confirmExplain;
    }

    @Override
    public String toString() {
        return "AqiFeedback [afId=" + afId + ", telId=" + telId + ", provinceId=" + provinceId + ", cityId=" + cityId + ", address=" + address + ", information=" + information + ", estimatedGrade=" + estimatedGrade + ", afDate=" + afDate + ", afTime=" + afTime + ", gmId=" + gmId + ", assignDate=" + assignDate + ", assignTime=" + assignTime + ", state=" + state + ", remarks=" + remarks + "]";
    }
}