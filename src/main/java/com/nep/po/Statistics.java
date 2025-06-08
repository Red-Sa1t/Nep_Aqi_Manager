package com.nep.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Statistics implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @JsonProperty("province_id")
    private Integer provinceId;

    @JsonProperty("city_id")
    private Integer cityId;

    private String address;

    @JsonProperty("so2_value")
    private Integer so2Value;

    @JsonProperty("so2_level")
    private Integer so2Level;

    @JsonProperty("co_value")
    private Integer coValue;

    @JsonProperty("co_level")
    private Integer coLevel;

    @JsonProperty("spm_value")
    private Integer spmValue;

    @JsonProperty("spm_level")
    private Integer spmLevel;

    @JsonProperty("aqi_id")
    private Integer aqiId;

    @JsonProperty("confirm_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date confirmDate;

    @JsonProperty("confirm_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Date confirmTime;

    @JsonProperty("gm_id")
    private Integer gmId;

    @JsonProperty("fd_id")
    private String fdId;

    private String information;
    private String remarks;

    public Statistics() {
    }

    public Statistics(Integer id, Integer provinceId, Integer cityId, String address, Integer so2Value, Integer so2Level, Integer coValue, Integer coLevel, Integer spmValue, Integer spmLevel, Integer aqiId, Date confirmDate, Date confirmTime, Integer gmId, String fdId, String information, String remarks) {
        this.id = id;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.address = address;
        this.so2Value = so2Value;
        this.so2Level = so2Level;
        this.coValue = coValue;
        this.coLevel = coLevel;
        this.spmValue = spmValue;
        this.spmLevel = spmLevel;
        this.aqiId = aqiId;
        this.confirmDate = confirmDate;
        this.confirmTime = confirmTime;
        this.gmId = gmId;
        this.fdId = fdId;
        this.information = information;
        this.remarks = remarks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getSo2Value() {
        return so2Value;
    }

    public void setSo2Value(Integer so2Value) {
        this.so2Value = so2Value;
    }

    public Integer getSo2Level() {
        return so2Level;
    }

    public void setSo2Level(Integer so2Level) {
        this.so2Level = so2Level;
    }

    public Integer getCoValue() {
        return coValue;
    }

    public void setCoValue(Integer coValue) {
        this.coValue = coValue;
    }

    public Integer getCoLevel() {
        return coLevel;
    }

    public void setCoLevel(Integer coLevel) {
        this.coLevel = coLevel;
    }

    public Integer getSpmValue() {
        return spmValue;
    }

    public void setSpmValue(Integer spmValue) {
        this.spmValue = spmValue;
    }

    public Integer getSpmLevel() {
        return spmLevel;
    }

    public void setSpmLevel(Integer spmLevel) {
        this.spmLevel = spmLevel;
    }

    public Integer getAqiId() {
        return aqiId;
    }

    public void setAqiId(Integer aqiId) {
        this.aqiId = aqiId;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Integer getGmId() {
        return gmId;
    }

    public void setGmId(Integer gmId) {
        this.gmId = gmId;
    }

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Statistics [id=" + id + ", provinceId=" + provinceId + ", cityId=" + cityId + ", address=" + address + ", so2Value=" + so2Value + ", so2Level=" + so2Level + ", coValue=" + coValue + ", coLevel=" + coLevel + ", spmValue=" + spmValue + ", spmLevel=" + spmLevel + ", aqiId=" + aqiId + ", confirmDate=" + confirmDate + ", confirmTime=" + confirmTime + ", gmId=" + gmId + ", fdId=" + fdId + ", information=" + information + ", remarks=" + remarks + "]";
    }
}