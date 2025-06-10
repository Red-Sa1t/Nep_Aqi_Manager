package com.nep.entity;

import java.io.Serializable;

public class Supervisor extends Operator implements Serializable {

    private static final long serialVersionUID = 1L;
    private String sex;    //���ڼලԱ�Ա�  ��/Ů

    public Supervisor() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Supervisor(String loginCode, String password, String realName, String sex) {
        super(loginCode, password, realName);
        this.sex = sex;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Supervisor [sex=" + sex + ", getLoginCode()=" + getLoginCode() + ", getPassword()=" + getPassword()
                + ", getRealName()=" + getRealName() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
                + ", toString()=" + super.toString() + "]";
    }

}
