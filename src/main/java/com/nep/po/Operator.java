package com.nep.po;

import java.io.Serializable;

public class Operator implements Serializable {

    private static final long serialVersionUID = 1L;
    private String loginCode;
    private String password;
    private String realName;

    public Operator() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Operator(String loginCode, String password, String realName) {
        super();
        this.loginCode = loginCode;
        this.password = password;
        this.realName = realName;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

}
