package com.nep.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Supervisor implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("tel_id")
    private String telId;

    private String password;

    @JsonProperty("real_name")
    private String realName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;

    private String sex;
    private String remarks;

    public Supervisor() {
    }

    public Supervisor(String telId, String password, String realName, Date birthday, String sex, String remarks) {
        this.telId = telId;
        this.password = password;
        this.realName = realName;
        this.birthday = birthday;
        this.sex = sex;
        this.remarks = remarks;
    }

    public String getTelId() {
        return telId;
    }

    public void setTelId(String telId) {
        this.telId = telId;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Supervisor [telId=" + telId + ", password=" + password + ", realName=" + realName + ", birthday=" + birthday + ", sex=" + sex + ", remarks=" + remarks + "]";
    }
}
