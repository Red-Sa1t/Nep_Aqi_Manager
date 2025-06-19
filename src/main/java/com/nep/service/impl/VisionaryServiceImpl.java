package com.nep.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.io.RWJsonTest;
import com.nep.po.Visionary;
import com.nep.service.VisionaryService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class VisionaryServiceImpl implements VisionaryService {

    public static ClassLoader classLoader = RWJsonTest.class.getClassLoader();

    public static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean login(String loginCode, String password) {
        List<Visionary> visionary = new ArrayList<>();
        try {
            InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/visionary.json");
            visionary = objectMapper.readValue(inputStream, new TypeReference<List<Visionary>>() {
            });
            for (Visionary a : visionary) {
                if (a.getLoginCode().equals(loginCode) && a.getPassword().equals(password)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
