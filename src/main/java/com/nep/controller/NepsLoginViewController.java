package com.nep.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.nep.util.JavafxUtil;
import com.nep.NepsMain;
import com.nep.service.SupervisorService;
import com.nep.service.impl.SupervisorServiceImpl;

public class NepsLoginViewController {

    @FXML
    private TextField txt_id;	//绑定登录账号文本框
    @FXML
    private PasswordField txt_password;	//绑定登录密码框

    //主舞台
    public static Stage primaryStage;
    //多态
    public SupervisorService supervisorService = new SupervisorServiceImpl();

    public TextField getTxt_id() {
        return txt_id;
    }
    public void setTxt_id(TextField txt_id) {
        this.txt_id = txt_id;
    }
    public PasswordField getTxt_password() {
        return txt_password;
    }
    public void setTxt_password(PasswordField txt_password) {
        this.txt_password = txt_password;
    }

    /**
     * 绑定登录按钮事件
     */
    public void login(){
        boolean flag = supervisorService.login(txt_id.getText(), txt_password.getText());
        if(flag){
            NepsSelectAqiViewController.primaryStage = primaryStage;
            JavafxUtil.showStage(NepsMain.class,"view/NepsSelectAqiView.fxml", primaryStage, "东软环保公众监督平台-公众监督员端-AQI数据反馈");
        }else{
            JavafxUtil.showAlert(primaryStage, "登录失败", "用户名密码错误", "","warn");
        }
    }

    /**
     * 绑定注册按钮事件
     */
    public void register(){
        //跳转到公众监督员注册界面
        NepsRegisterViewController.primaryStage = primaryStage;
        JavafxUtil.showStage(NepsMain.class,"view/NepsRegisterView.fxml", primaryStage, "东软环保公众监督平台-公众监督员端-公众监督员注册");
    }
}

