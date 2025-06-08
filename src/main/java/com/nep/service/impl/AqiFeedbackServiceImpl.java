package com.nep.service.impl;

//import com.nep.entity.AqiFeedback;
import com.nep.po.AqiFeedback;
import com.nep.util.FileUtil;
import com.nep.service.AqiFeedbackService;

import java.util.List;

public class AqiFeedbackServiceImpl implements AqiFeedbackService {
    @Override
    public void saveFeedBack(AqiFeedback afb) {
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";

        List<AqiFeedback> afList = (List<AqiFeedback>) FileUtil.readObject(ProPaht+"aqifeedback.txt");
        afb.setAfId(afList.size()+1);
        afList.add(afb);
        FileUtil.writeObject(ProPaht+"aqifeedback.txt", afList);
    }

    @Override
    public void assignGridMember(String afId,String realName) {
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";

        List<AqiFeedback> alist = (List<AqiFeedback>)FileUtil.readObject(ProPaht+"aqifeedback.txt");
        for (AqiFeedback af : alist) {
            if(af.getAfId().toString().equals(afId)){
                af.setGmName(realName);
                af.setState("已指派");
                break;
            }
        }

        FileUtil.writeObject(ProPaht+"aqifeedback.txt", alist);
    }

    @Override
    public void confirmData(AqiFeedback afb) {
        // TODO Auto-generated method stub
        System.out.println(afb.getAfId());
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<AqiFeedback> afList = (List<AqiFeedback>)FileUtil.readObject(ProPaht+"aqifeedback.txt");
        for(int i = 0; i< afList.size();i++){
            AqiFeedback a = afList.get(i);
            if(a.getGmName() != null && a.getGmName().equals(afb.getGmName()) && a.getAfId().intValue()==afb.getAfId().intValue()){
                a.setState(afb.getState());
                a.setConfirmDate(afb.getConfirmDate());
                a.setCo(afb.getCo());
                a.setSo2(afb.getSo2());
                a.setPm(afb.getPm());
                a.setConfirmLevel(afb.getConfirmLevel());
                a.setConfirmExplain(afb.getConfirmExplain());
                break;
            }
        }
        FileUtil.writeObject(ProPaht+"aqifeedback.txt", afList);
    }
}
