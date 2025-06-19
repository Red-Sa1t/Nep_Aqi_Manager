package com.nep.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.io.RWJsonTest;
import com.nep.manager.TipsManager;
import com.nep.po.AqiFeedback;
import com.nep.po.GridMember;
import com.nep.service.AqiFeedbackService;
import com.nep.service.impl.AqiFeedbackServiceImpl;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NepmAqiAssignViewController implements Initializable {
    @FXML
    private Pane txt_pane1;
    @FXML
    private Pane txt_pane2;
    @FXML
    private Pane txt_pane3;
    @FXML
    private TextField txt_afId;
    @FXML
    private Label label_afId;
    @FXML
    private Label  label_afName;
    @FXML
    private Label  label_proviceName;
    @FXML
    private Label  label_cityName;
    @FXML
    private Label  label_address;
    @FXML
    private Label  label_infomation;
    @FXML
    private Label  label_estimateGrade;
    @FXML
    private Label  label_date;
    @FXML
    private MFXComboBox<String> combo_realName;
    //当前舞台
    public static Stage aqiInfoStage;
    //多态
    private AqiFeedbackService aqiFeedbackService = new AqiFeedbackServiceImpl();
    public Pane getTxt_pane1() {
        return txt_pane1;
    }
    public void setTxt_pane1(Pane txt_pane1) {
        this.txt_pane1 = txt_pane1;
    }
    public Pane getTxt_pane2() {
        return txt_pane2;
    }
    public void setTxt_pane2(Pane txt_pane2) {
        this.txt_pane2 = txt_pane2;
    }
    public Pane getTxt_pane3() {
        return txt_pane3;
    }
    public void setTxt_pane3(Pane txt_pane3) {
        this.txt_pane3 = txt_pane3;
    }

    public static ClassLoader classLoader = RWJsonTest.class.getClassLoader();

    public static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //初始化三个pane容器样式
//        txt_pane1.setStyle("-fx-border-color: #CCC;");
//        txt_pane2.setStyle("-fx-background-color: #CCC;");
//        txt_pane3.setStyle("-fx-border-color: #CCC;");
        //标签初始化
        initConroller();
        //初始化网格员
        List<GridMember> glist = new ArrayList<>();
        try {
            InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/grid_member.json");
            glist = objectMapper.readValue(inputStream, new TypeReference<List<GridMember>>() {
            });
            for (GridMember gm : glist) {
                if (gm.getState().equals("工作中")) {
                    combo_realName.getItems().add(gm.getRealName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queryFeedback(){
        String afId = txt_afId.getText();

        List<AqiFeedback> alist = new ArrayList<>();
        try {
            InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/aqi_feedback.json");
            alist = objectMapper.readValue(inputStream, new TypeReference<List<AqiFeedback>>() {
            });

        boolean flag = true;
        for (AqiFeedback af : alist) {
            if (af.getAfId().toString().equals(afId)) {
                flag = false;
                label_afId.setText(af.getAfId()+"");
                label_afName.setText(af.getAfName());
                label_address.setText(af.getAddress());
                label_cityName.setText(af.getCityName());
                label_date.setText(af.getDate());
                label_estimateGrade.setText(af.getEstimateGrade());
                label_infomation.setText(af.getInfomation());
                label_proviceName.setText(af.getProviceName());
                break;
            }
        }
        if(flag){
            TipsManager.getInstance().showError("未找到当前编号反馈信息");
            initConroller();
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assignGridMember(){
        if(label_afId.getText().equals("无")){
            TipsManager.getInstance().showError("未找到要指派的反馈信息");
            return;
        }
        if(combo_realName.getValue().equals("请选择网格员")){
            TipsManager.getInstance().showError("未指派网格员");
            return;
        }
        String afId = label_afId.getText();
        aqiFeedbackService.assignGridMember(afId, combo_realName.getValue());
        TipsManager.getInstance().showInfo("网格员指派成功");
        initConroller();
        NepmAqiInfoViewController.getInstance().RefreshTable();
    }

    // 界面空间初始化方法
    public void initConroller(){
        txt_afId.setText("");
        label_afId.setText("无");
        label_afName.setText("无");
        label_address.setText("无");
        label_cityName.setText("无");
        label_date.setText("无");
        label_estimateGrade.setText("无");
        label_infomation.setText("无");
        label_proviceName.setText("无");
        combo_realName.setValue("请选择网格员");
    }

    public void handleEnterKey(KeyEvent keyEvent) {
        assignGridMember();
    }
}
