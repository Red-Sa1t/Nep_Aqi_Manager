package com.nep.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.io.RWJsonTest;
import com.nep.po.AqiFinish;
import com.nep.service.AqiFinishService;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AqiFinishServiceImpl implements AqiFinishService {
    public static ClassLoader classLoader = RWJsonTest.class.getClassLoader();
    public static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void confirmData(AqiFinish afb) {
        List<AqiFinish> afList = new ArrayList<>();
        try {
            InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/aqi_finish.json");
            afList = objectMapper.readValue(inputStream, new TypeReference<List<AqiFinish>>() {
            });
            afb.setAfId(afList.size() + 1);
            afList.add(afb);

            for (int i = 0; i < afList.size(); i++) {
                AqiFinish a = afList.get(i);
                if (a.getGmName() != null && a.getGmName().equals(afb.getGmName()) && a.getAfId().intValue() == afb.getAfId().intValue()) {
                    a.setState(afb.getState());
                    a.setConfirmDate(afb.getConfirmDate());
                    a.setCo(afb.getCo());
                    a.setSo2(afb.getSo2());
                    a.setPm(afb.getPm());
                    a.setConfirmLevel(afb.getConfirmLevel());
                    a.setConfirmExplain(afb.getConfirmExplain());
                    a.setCo_level(afb.getCo_level());
                    a.setSo2_level(afb.getSo2_level());
                    a.setPm_level(afb.getPm_level());
                    break;
                }
            }
            OutputStream outputStream =
                    new FileOutputStream(RWJsonTest.class.getClassLoader().getResource("NepDatas/JSONData/aqi_finish.json").getFile());

            objectMapper.writeValue(outputStream, afList);

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
