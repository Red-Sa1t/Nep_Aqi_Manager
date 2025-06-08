package com.nep.service.impl;

//import com.nep.entity.GridMember;
import com.nep.po.GridMember;
import com.nep.util.FileUtil;
import com.nep.service.GridMemberService;

import java.util.List;

public class GridMemberServiceImpl implements GridMemberService {
    @Override
    public GridMember login(String loginCode, String password) {
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";

        List<GridMember> glist = (List<GridMember>) FileUtil.readObject(ProPaht+"gridmember.txt");
        for(GridMember gm : glist){
            if(gm.getTel().equals(loginCode) && gm.getPassword().equals(password)){
                return gm;
            }
        }
        return null;
    }
}
