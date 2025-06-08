package com.nep.service.impl;

import com.nep.controller.NepsFeedbackViewController;
import com.nep.controller.NepsSelectAqiViewController;
//import com.nep.entity.Supervisor;
import com.nep.po.Supervisor;
import com.nep.util.FileUtil;
import com.nep.service.SupervisorService;

import java.util.List;

public class SupervisorServiceImpl implements SupervisorService {
    @Override
    public boolean login(String loginCode,String password) {
        // TODO Auto-generated method stub
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<Supervisor> slist =(List<Supervisor>) FileUtil.readObject(ProPaht+"supervisor.txt");
        boolean isLogin = false;
        for(Supervisor s:slist){
            if(s.getTelId().equals(loginCode) && s.getPassword().equals(password)){
                NepsSelectAqiViewController.supervisor = s;	//将当前登录成功用户身份共享给下一个界面
                NepsFeedbackViewController.supervisor = s;	//将当前登录成功用户身份共享给下一个界面
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean register(Supervisor supervisor) {
        // TODO Auto-generated method stub
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<Supervisor> slist = (List<Supervisor>)FileUtil.readObject(ProPaht+"supervisor.txt");
        System.out.println(slist.size());
        for(Supervisor s:slist){
            if(s.getTelId().equals(supervisor.getTelId())){
                return false;
            }
        }
        slist.add(supervisor);
        FileUtil.writeObject(ProPaht+"supervisor.txt", slist);
        return true;
    }
}
