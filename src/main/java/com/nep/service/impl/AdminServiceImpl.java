package com.nep.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.entity.Admin;
import com.nep.io.RWJsonTest;
import com.nep.service.AdminService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AdminServiceImpl implements AdminService {

    public static ClassLoader classLoader = RWJsonTest.class.getClassLoader();

    public static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean login(String loginCode, String password) {
        List<Admin> admin = new ArrayList<>();
        try {
            InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/admins.json");
            admin = objectMapper.readValue(inputStream, new TypeReference<List<Admin>>() {
            });
            for (Admin a : admin) {
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
