package com.nep.controller;

import com.nep.po.AqiFeedback;
import com.nep.po.GridMember;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.nep.util.JavafxUtil;
//import com.nep.entity.AqiFeedback;
//import com.nep.entity.GridMember;
import com.nep.util.FileUtil;
import com.nep.service.AqiFeedbackService;
import com.nep.service.impl.AqiFeedbackServiceImpl;

import java.net.URL;
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
    private ComboBox<String> combo_realName;
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //初始化三个pane容器样式
        txt_pane1.setStyle("-fx-border-color: #CCC;");
        txt_pane2.setStyle("-fx-background-color: #CCC;");
        txt_pane3.setStyle("-fx-border-color: #CCC;");
        //标签初始化
        initConroller();
        //初始化网格员
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<GridMember> glist = (List<GridMember>) FileUtil.readObject(ProPaht+"gridmember.txt");
        for (GridMember gm : glist) {
            if(gm.getState().equals("工作中")){
                combo_realName.getItems().add(gm.getGmName());
            }
        }
    }

    public void queryFeedback(){
        String afId = txt_afId.getText();
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";

        List<AqiFeedback> alist = (List<AqiFeedback>)FileUtil.readObject(ProPaht+"aqifeedback.txt");
        boolean flag = true;
        for (AqiFeedback af : alist) {
            if(af.getAfId().toString().equals(afId) && af.getState().equals("未指派")){
                flag = false;
                label_afId.setText(af.getAfId()+"");
                label_afName.setText(af.getAfName());
                label_address.setText(af.getAddress());
                label_cityName.setText(af.getCityName());
                label_date.setText(af.getAssignDate().toString());
                label_estimateGrade.setText(af.getEstimatedGrade().toString());
                label_infomation.setText(af.getInformation());
                label_proviceName.setText(af.getProvinceName());
                break;
            }

        }
        if(flag){
            JavafxUtil.showAlert(aqiInfoStage, "查询失败", "未找到当前编号反馈信息", "请重新输入AQI反馈数据编号","warn");
            initConroller();
        }
    }

    public void assignGridMember(){
        //前做判断
        if(label_afId.getText().equals("无")){
            JavafxUtil.showAlert(aqiInfoStage, "指派失败", "未找到要指派的反馈信息", "请选择要指派的反馈信息","warn");
            return;
        }
        if(combo_realName.getValue().equals("请选择网格员")){
            JavafxUtil.showAlert(aqiInfoStage, "指派失败", "您没有选择要指派的网格员", "请选择您要指派的网格员","warn");
            return;
        }
        String afId = label_afId.getText();
        aqiFeedbackService.assignGridMember(afId, combo_realName.getValue());
        JavafxUtil.showAlert(aqiInfoStage, "指派成功", "AQI反馈信息指派成功!", "请等待网格员实测数据信息","info");
        initConroller();
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
}
