package com.nep.po;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GridMember implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("gm_id")
    private Integer gmId;

    @JsonProperty("gm_name")
    private String gmName;

    @JsonProperty("gm_code")
    private String gmCode;

    private String password;

    @JsonProperty("province_id")
    private Integer provinceId;

    @JsonProperty("city_id")
    private Integer cityId;

    private String tel;
    private Integer state;
    private String remarks;

    public GridMember() {
    }

    public GridMember(Integer gmId, String gmName, String gmCode, String password, Integer provinceId, Integer cityId, String tel, Integer state, String remarks) {
        this.gmId = gmId;
        this.gmName = gmName;
        this.gmCode = gmCode;
        this.password = password;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.tel = tel;
        this.state = state;
        this.remarks = remarks;
    }

    public Integer getGmId() {
        return gmId;
    }

    public void setGmId(Integer gmId) {
        this.gmId = gmId;
    }

    public String getGmName() {
        return gmName;
    }

    public void setGmName(String gmName) {
        this.gmName = gmName;
    }

    public String getGmCode() {
        return gmCode;
    }

    public void setGmCode(String gmCode) {
        this.gmCode = gmCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "GridMember [gmId=" + gmId + ", gmName=" + gmName + ", gmCode=" + gmCode + ", password=" + password + ", provinceId=" + provinceId + ", cityId=" + cityId + ", tel=" + tel + ", state=" + state + ", remarks=" + remarks + "]";
    }
}