package com.nep.entity;

import java.io.Serializable;

public class GridMember extends Operator implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String gmTel;    //����Ա��ϵ�绰
    private String state;    //����Ա״̬: ������,�ݼ���

    public String getGmTel() {
        return gmTel;
    }

    public void setGmTel(String gmTel) {
        this.gmTel = gmTel;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "GridMember [gmTel=" + gmTel + ", state=" + state + ", getLoginCode()=" + getLoginCode()
                + ", getPassword()=" + getPassword() + ", getRealName()=" + getRealName() + "]";
    }

}
