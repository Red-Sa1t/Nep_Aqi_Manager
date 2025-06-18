package com.nep.po;

import java.io.Serializable;

public class AqiFeedback implements Serializable {

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


    @Override
    public String toString() {
        return "AqiFeedback [afId=" + afId + ", afName=" + afName + ", proviceName=" + proviceName + ", cityName="
                + cityName + ", address=" + address + ", infomation=" + infomation + ", estimateGrade=" + estimateGrade
                + ", date=" + date + ", state=" + state + ", gmName=" + gmName + "]";
    }


}
