package com.nep.po;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GridCity implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("city_id")
    private Integer cityId;

    @JsonProperty("city_name")
    private String cityName;

    @JsonProperty("province_id")
    private Integer provinceId;

    private String remarks;

    public GridCity() {
    }

    public GridCity(Integer cityId, String cityName, Integer provinceId, String remarks) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.provinceId = provinceId;
        this.remarks = remarks;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "GridCity [cityId=" + cityId + ", cityName=" + cityName + ", provinceId=" + provinceId + ", remarks=" + remarks + "]";
    }
}
