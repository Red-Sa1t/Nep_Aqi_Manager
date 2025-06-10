package com.nep.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Admin extends Operator implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("loginCode")
    private String loginCode;

    @JsonProperty("password")
    private String password;

    @JsonProperty("realName")
    private String realName;

    public Admin() {
        super();
    }

    public Admin(String loginCode, String password, String realName) {
        super(loginCode, password, realName);
    }

    public String getLoginCode() {
        return this.loginCode;
    }

    public void setLoginCode(String adminCode) {
        this.loginCode = adminCode;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String remarks) {
        this.realName = remarks;
    }

    @Override
    public String toString() {
        return "Admin [getLoginCode()=" + getLoginCode() + ", getPassword()=" + getPassword() + ", getRealName()="
                + getRealName() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
                + super.toString() + "]";
    }

}
