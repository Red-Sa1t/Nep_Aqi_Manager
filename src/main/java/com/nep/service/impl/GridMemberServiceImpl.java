package com.nep.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.entity.GridMember;
import com.nep.io.RWJsonTest;
import com.nep.service.GridMemberService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GridMemberServiceImpl implements GridMemberService {
    public static ClassLoader classLoader = RWJsonTest.class.getClassLoader();
    public static ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public GridMember login(String loginCode, String password) {
        List<GridMember> glist = new ArrayList<>();
        try {
            InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/grid_member.json");
            glist = objectMapper.readValue(inputStream, new TypeReference<List<GridMember>>() {
            });
            for (GridMember gm : glist) {
                if (gm.getGmTel().equals(loginCode) && gm.getPassword().equals(password)) {
                    return gm;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
