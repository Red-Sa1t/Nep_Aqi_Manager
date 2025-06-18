package com.nep.service;

import com.nep.po.AqiFeedback;

public interface AqiFeedbackService {
    /**
     * 添加反馈信息
     * @param afb
     */
    public void saveFeedBack(AqiFeedback afb);
    /**
     * 指派网格员,修改反馈信息中网格员和状态
     */
    public void assignGridMember(String afId,String realName);
}
