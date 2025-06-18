package com.nep.po;

import java.io.Serializable;

public class AqiFinish implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer afId;
    private String afName;
    private String proviceName;
    private String cityName;
    private String address;
    private String infomation;
    private String estimateGrade;
    private String date;
    private String state;
    private String gmName;
    private String confirmDate;
    private Double so2;
    private Double co;
    private Double pm;
    private int so2_level;
    private int co_level;
    private int pm_level;
    private String confirmLevel;
    private String confirmExplain;

    public Integer getAfId() {
        return afId;
    }

    public void setAfId(Integer afId) {
        this.afId = afId;
    }

    public String getAfName() {
        return afName;
    }

    public void setAfName(String afName) {
        this.afName = afName;
    }

    public String getProviceName() {
        return proviceName;
    }

    public void setProviceName(String proviceName) {
        this.proviceName = proviceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfomation() {
        return infomation;
    }

    public void setInfomation(String infomation) {
        this.infomation = infomation;
    }

    public String getEstimateGrade() {
        return estimateGrade;
    }

    public void setEstimateGrade(String estimateGrade) {
        this.estimateGrade = estimateGrade;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGmName() {
        return gmName;
    }

    public void setGmName(String gmName) {
        this.gmName = gmName;
    }

    public Double getSo2() {
        return so2;
    }

    public void setSo2(Double so2) {
        this.so2 = so2;
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

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
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

    public int getSo2_level() {
        return so2_level;
    }

    public void setSo2_level(int so2_level) {
        this.so2_level = so2_level;
    }

    public int getCo_level() {
        return co_level;
    }

    public void setCo_level(int co_level) {
        this.co_level = co_level;
    }

    public int getPm_level() {
        return pm_level;
    }

    public void setPm_level(int pm_level) {
        this.pm_level = pm_level;
    }

    @Override
    public String toString() {
        return "AqiFinish{" +
                "afId=" + afId +
                ", afName='" + afName + '\'' +
                ", proviceName='" + proviceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", address='" + address + '\'' +
                ", infomation='" + infomation + '\'' +
                ", estimateGrade='" + estimateGrade + '\'' +
                ", date='" + date + '\'' +
                ", state='" + state + '\'' +
                ", gmName='" + gmName + '\'' +
                ", confirmDate='" + confirmDate + '\'' +
                ", so2=" + so2 +
                ", co=" + co +
                ", pm=" + pm +
                ", so2_level=" + so2_level +
                ", co_level=" + co_level +
                ", pm_level=" + pm_level +
                ", confirmLevel='" + confirmLevel + '\'' +
                ", confirmExplain='" + confirmExplain + '\'' +
                '}';
    }
}
