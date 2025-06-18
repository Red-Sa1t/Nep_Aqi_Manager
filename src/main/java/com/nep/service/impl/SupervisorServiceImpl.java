package com.nep.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.controller.NepsFeedbackViewController;
import com.nep.controller.NepsReportDetailViewController;
import com.nep.controller.NepsSelectAqiViewController;
import com.nep.io.RWJsonTest;
import com.nep.po.Supervisor;
import com.nep.service.SupervisorService;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SupervisorServiceImpl implements SupervisorService {

    public static ClassLoader classLoader = RWJsonTest.class.getClassLoader();

    public static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean login(String loginCode,String password) {
        List<Supervisor> slist = new ArrayList<>();
        try {
            InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/supervisor.json");
            slist = objectMapper.readValue(inputStream, new TypeReference<List<Supervisor>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isLogin = false;
        for(Supervisor s:slist){
            if (s.getLoginCode().equals(loginCode) && s.getPassword().equals(password)) {
                NepsSelectAqiViewController.supervisor = s;
                NepsFeedbackViewController.supervisor = s;
                NepsReportDetailViewController.supervisor = s;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean register(Supervisor supervisor) {
        List<Supervisor> slist = new ArrayList<>();
        try {
            InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/supervisor.json");
            slist = objectMapper.readValue(inputStream, new TypeReference<List<Supervisor>>() {
            });
            for (Supervisor s : slist) {
                if (s.getLoginCode().equals(supervisor.getLoginCode())) {
                    return false;
                }
            }
            slist.add(supervisor);
            Path path = Paths.get("src/main/resources/NepDatas/JSONData/supervisor.json");
            OutputStream outputStream = new FileOutputStream(path.toFile());
            objectMapper.writeValue(outputStream, slist);

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
