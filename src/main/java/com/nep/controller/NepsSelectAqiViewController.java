package com.nep.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.NepsMain;
import com.nep.entity.Aqi;
import com.nep.entity.AqiFeedback;
import com.nep.entity.ProvinceCity;
import com.nep.entity.Supervisor;
import com.nep.io.RWJsonTest;
import com.nep.service.AqiFeedbackService;
import com.nep.service.impl.AqiFeedbackServiceImpl;
import com.nep.util.CommonUtil;
import com.nep.util.JavafxUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class NepsSelectAqiViewController implements Initializable {
    @FXML
    private TableView<Aqi> txt_tableView;
    @FXML
    private ComboBox<String> txt_province;
    @FXML
    private ComboBox<String> txt_city;
    @FXML
    private TextField txt_address;
    @FXML
    private ComboBox<String> txt_level;
    @FXML
    private TextArea txt_information;
    @FXML
    private Label label_realName;
    //主舞台
    public static Stage primaryStage;
    //当前登录成功的公众监督员用户身份
    public static Supervisor supervisor;

    public static ClassLoader classLoader = RWJsonTest.class.getClassLoader();

    public static ObjectMapper objectMapper = new ObjectMapper();
    //多态
    private AqiFeedbackService aqiFeedbackService = new AqiFeedbackServiceImpl();

    public TableView<Aqi> getTxt_tableView() {
        return txt_tableView;
    }
    public void setTxt_tableView(TableView<Aqi> txt_tableView) {
        this.txt_tableView = txt_tableView;
    }
    public ComboBox<String> getTxt_province() {
        return txt_province;
    }
    public void setTxt_province(ComboBox<String> txt_province) {
        this.txt_province = txt_province;
    }
    public ComboBox<String> getTxt_city() {
        return txt_city;
    }
    public void setTxt_city(ComboBox<String> txt_city) {
        this.txt_city = txt_city;
    }
    public TextField getTxt_address() {
        return txt_address;
    }
    public void setTxt_address(TextField txt_address) {
        this.txt_address = txt_address;
    }
    public ComboBox<String> getTxt_level() {
        return txt_level;
    }
    public void setTxt_level(ComboBox<String> txt_level) {
        this.txt_level = txt_level;
    }
    public TextArea getTxt_information() {
        return txt_information;
    }
    public void setTxt_information(TextArea txt_information) {
        this.txt_information = txt_information;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        label_realName.setText(supervisor.getRealName());
        //初始化表格
        TableColumn<Aqi, String> levelColumn = new TableColumn<>("级别");
        levelColumn.setMinWidth(80);
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));

        TableColumn<Aqi, String> explainColumn = new TableColumn<>("说明");
        explainColumn.setMinWidth(80);
        explainColumn.setCellValueFactory(new PropertyValueFactory<>("explain"));

        TableColumn<Aqi, String> impactColumn = new TableColumn<>("对健康影响");
        impactColumn.setMinWidth(425);
        impactColumn.setCellValueFactory(new PropertyValueFactory<>("impact"));

        txt_tableView.getColumns().addAll(levelColumn, explainColumn,impactColumn);
        ObservableList<Aqi> data = FXCollections.observableArrayList();

        List<Aqi> alist = new ArrayList<>();
        try {
            InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/aqi.json");
            alist = objectMapper.readValue(inputStream, new TypeReference<List<Aqi>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(Aqi aqi:alist){
            data.add(aqi);
        }
        txt_tableView.setItems(data);
        //初始化AQI等级下拉列表
        for(Aqi aqi:alist){
            txt_level.getItems().add(aqi.getLevel());
        }
        txt_level.setValue("预估AQI等级");

        List<ProvinceCity> plist = new ArrayList<>();
        try {
            InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/province_city.json");
            plist = objectMapper.readValue(inputStream, new TypeReference<List<ProvinceCity>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (ProvinceCity province : plist) {
            txt_province.getItems().add(province.getProvinceName());
        }
        txt_province.setValue("请选择省区域");
        txt_city.setValue("请选择市区域");
        List<ProvinceCity> finalPlist = plist;
        txt_province.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // TODO Auto-generated method stub
                txt_city.setItems(FXCollections.observableArrayList());	//先清空
                List<String> clist = new ArrayList<String>();
                for (int i = 0; i < finalPlist.size(); i++) {
                    if (newValue.equals(finalPlist.get(i).getProvinceName())) {
                        clist = finalPlist.get(i).getCityName();
                    }
                }
                for(String cityName:clist){
                    txt_city.getItems().add(cityName);
                }
                txt_city.setValue("请选择市区域");
            }

        });


    }

    public void saveFeedBack(){
        AqiFeedback afb = new AqiFeedback();
        afb.setAddress(txt_address.getText());
        afb.setAfName(supervisor.getRealName());
        afb.setProviceName(txt_province.getValue());
        afb.setCityName(txt_city.getValue());
        afb.setEstimateGrade(txt_level.getValue());
        afb.setInfomation(txt_information.getText());
        afb.setDate(CommonUtil.currentDate());
        afb.setState("未指派");
        aqiFeedbackService.saveFeedBack(afb);
        JavafxUtil.showAlert(primaryStage, "反馈信息成功", "您的预估AQI信息提交成功", "感谢您的反馈!","info");
        NepsFeedbackViewController.primaryStage = primaryStage;
        JavafxUtil.showStage(NepsMain.class,"view/NepsFeedbackView.fxml", primaryStage,"东软环保公众监督平台-公众监督员端-AQI反馈数据列表");

    }

    public void feedBackList(){
        NepsFeedbackViewController.primaryStage = primaryStage;
        JavafxUtil.showStage(NepsMain.class,"view/NepsFeedbackView.fxml", primaryStage,"东软环保公众监督平台-公众监督员端-AQI反馈数据列表");
    }

}

