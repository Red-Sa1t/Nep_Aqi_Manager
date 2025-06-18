package com.nep.service;

import com.nep.po.AqiFinish;

public interface AqiFinishService {
    /**
     * 提交实测AQI数据
     *
     * @param afb
     */
    public void confirmData(AqiFinish afb);
}
