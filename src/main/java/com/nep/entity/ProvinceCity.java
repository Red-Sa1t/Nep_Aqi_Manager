package com.nep.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProvinceCity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer provinceId;
    private String provinceName;
    private List<String> cityName = new ArrayList<String>();

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public List<String> getCityName() {
        return cityName;
    }

    public void setCityName(List<String> cityName) {
        this.cityName = cityName;
    }

}
