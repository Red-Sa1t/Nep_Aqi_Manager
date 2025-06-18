package com.nep.controller;

import com.nep.manager.TipsManager;
import com.nep.po.Supervisor;
import com.nep.service.SupervisorService;
import com.nep.service.impl.SupervisorServiceImpl;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NepsRegisterViewController {
    @FXML
    private TextField txt_id;
    @FXML
    private MFXPasswordField txt_password;
    @FXML
    private MFXPasswordField txt_repassword;
    @FXML
    private TextField txt_realName;
    @FXML
    private MFXRadioButton txt_sex;
    //主舞台
    public static Stage primaryStage;
    //多态
    private SupervisorService supervisorService = new SupervisorServiceImpl();
    public TextField getTxt_id() {
        return txt_id;
    }
    public void setTxt_id(TextField txt_id) {
        this.txt_id = txt_id;
    }

    public MFXPasswordField getTxt_password() {
        return txt_password;
    }

    public void setTxt_password(MFXPasswordField txt_password) {
        this.txt_password = txt_password;
    }

    public MFXPasswordField getTxt_repassword() {
        return txt_repassword;
    }

    public void setTxt_repassword(MFXPasswordField txt_repassword) {
        this.txt_repassword = txt_repassword;
    }
    public TextField getTxt_realName() {
        return txt_realName;
    }
    public void setTxt_realName(TextField txt_realName) {
        this.txt_realName = txt_realName;
    }

    public void register(){
        if(!txt_password.getText().equals(txt_repassword.getText())){
            TipsManager.getInstance().showError("两次密码不一致");
            txt_repassword.setText("");
            return;
        }
        Supervisor supervisor = new Supervisor();
        supervisor.setLoginCode(txt_id.getText());
        supervisor.setPassword(txt_password.getText());
        supervisor.setRealName(txt_realName.getText());
        supervisor.setSex(txt_sex.getText());
        boolean flag = supervisorService.register(supervisor);
        if(flag){
            TipsManager.getInstance().showInfo("注册成功！");
        }else{
            TipsManager.getInstance().showError("手机号已被注册！");
            txt_id.setText("");
            return;
        }
    }
}
