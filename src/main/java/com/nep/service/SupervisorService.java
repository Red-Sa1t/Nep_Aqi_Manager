package com.nep.service;

import com.nep.entity.Supervisor;

//import com.nep.po.Supervisor;

public interface SupervisorService {
    /**
     * 公众监督员登录功能
     * @return
     */
    public boolean login(String loginCode,String password);

    /**
     * 公众监督员注册功能
     * @param supervisor
     * @return
     */
    public boolean register(Supervisor supervisor);

}
