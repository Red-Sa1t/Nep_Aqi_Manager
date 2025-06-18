package com.nep.service.impl;

//import com.nep.entity.AqiFeedback;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.io.RWJsonTest;
import com.nep.po.AqiFeedback;
import com.nep.service.AqiFeedbackService;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AqiFeedbackServiceImpl implements AqiFeedbackService {
    public static ClassLoader classLoader = RWJsonTest.class.getClassLoader();
    public static ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void saveFeedBack(AqiFeedback afb) {
        List<AqiFeedback> afList = new ArrayList<>();
        try {
            InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/aqi_feedback.json");
            afList = objectMapper.readValue(inputStream, new TypeReference<List<AqiFeedback>>() {
            });
            afb.setAfId(afList.size() + 1);
            afList.add(afb);
            OutputStream outputStream =
                    new FileOutputStream(RWJsonTest.class.getClassLoader().getResource("NepDatas/JSONData/aqi_feedback.json").getFile());

            objectMapper.writeValue(outputStream, afList);

            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void assignGridMember(String afId,String realName) {
        List<AqiFeedback> afList = new ArrayList<>();
        try {
            InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/api_feedback.json");
            afList = objectMapper.readValue(inputStream, new TypeReference<List<AqiFeedback>>() {
            });

            for (AqiFeedback af : afList) {
                if (af.getAfId().toString().equals(afId)) {
                    af.setGmName(realName);
                    af.setState("已指派");
                    break;
                }
            }

            OutputStream outputStream =
                    new FileOutputStream(RWJsonTest.class.getClassLoader().getResource("NepDatas/JSONData/aqi_feedback.json").getFile());

            objectMapper.writeValue(outputStream, afList);

            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
