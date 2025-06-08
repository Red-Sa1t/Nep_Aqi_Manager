package com.nep.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.nep.util.JavafxUtil;
import com.nep.NepmMain;

import java.net.URL;
import java.util.ResourceBundle;

public class NepmMainViewController implements Initializable {

    //主舞台
    public static Stage primaryStage;
    @FXML
    private ImageView txt_imageView;

    public ImageView getTxt_imageView() {
        return txt_imageView;
    }

    public void setTxt_imageView(ImageView txt_imageView) {
        this.txt_imageView = txt_imageView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //初始化图片
        Image image = new Image("image/welcome.png");
        txt_imageView.setImage(image);
        txt_imageView.setPreserveRatio(false);   //禁用保持纵横比
    }

    public void aqiInfo(){
        JavafxUtil.showSubStage(NepmMain.class, "view/NepmAqiInfoView.fxml", primaryStage, "东软环保公众监督平台-管理端-AQI反馈数据查询");
    }

    public void aqiAssign(){
        NepmAqiAssignViewController.aqiInfoStage = JavafxUtil.showSubStage(NepmMain.class, "view/NepmAqiAssignView.fxml", primaryStage, "东软环保公众监督平台-管理端-AQI反馈数据指派");;
    }

    public void aqiConfirm(){
        JavafxUtil.showSubStage(NepmMain.class, "view/NepmConfirmInfoView.fxml", primaryStage, "东软环保公众监督平台-管理端-AQI实测数据查询");
    }
}

