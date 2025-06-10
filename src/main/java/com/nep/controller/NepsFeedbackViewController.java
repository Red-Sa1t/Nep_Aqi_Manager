package com.nep.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nep.NepsMain;
import com.nep.entity.AqiFeedback;
import com.nep.entity.Supervisor;
import com.nep.util.JavafxUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.nep.controller.NepmConfirmInfoViewController.classLoader;
import static com.nep.controller.NepmConfirmInfoViewController.objectMapper;

public class NepsFeedbackViewController implements Initializable {
    @FXML
    private TableView<AqiFeedback> txt_tableView;
    @FXML
    private Label txt_realName;
    //主舞台
    public static Stage primaryStage;
    //当前登录成功的公众监督员用户身份
    public static Supervisor supervisor;

    public TableView<AqiFeedback> getTxt_tableView() {
        return txt_tableView;
    }

    public void setTxt_tableView(TableView<AqiFeedback> txt_tableView) {
        this.txt_tableView = txt_tableView;
    }

    public Label getTxt_realName() {
        return txt_realName;
    }

    public void setTxt_realName(Label txt_realName) {
        this.txt_realName = txt_realName;
    }


    public void back(){
        JavafxUtil.showStage(NepsMain.class,"view/NepsSelectAqiView.fxml", primaryStage,"东软环保公众监督平台-公众监督员端-AQI数据反馈");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        //初始化当前用户名
        txt_realName.setText(supervisor.getRealName());

        //初始化table 数据表
        TableColumn<AqiFeedback, Integer> afIdColumn = new TableColumn<>("编号");
        afIdColumn.setMinWidth(40);
        afIdColumn.setStyle("-fx-alignment: center;");	//居中
        afIdColumn.setCellValueFactory(new PropertyValueFactory<>("afId"));

        TableColumn<AqiFeedback, String> proviceNameColumn = new TableColumn<>("省区域");
        proviceNameColumn.setMinWidth(60);
        proviceNameColumn.setStyle("-fx-alignment: center;");	//居中
        proviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("proviceName"));

        TableColumn<AqiFeedback, String> cityNameColumn = new TableColumn<>("市区域");
        cityNameColumn.setMinWidth(60);
        cityNameColumn.setStyle("-fx-alignment: center;");	//居中
        cityNameColumn.setCellValueFactory(new PropertyValueFactory<>("cityName"));

        TableColumn<AqiFeedback, String> estimateGradeColumn = new TableColumn<>("预估等级");
        estimateGradeColumn.setMinWidth(60);
        estimateGradeColumn.setStyle("-fx-alignment: center;");	//居中
        estimateGradeColumn.setCellValueFactory(new PropertyValueFactory<>("estimateGrade"));

        TableColumn<AqiFeedback, String> dateColumn = new TableColumn<>("反馈时间");
        dateColumn.setMinWidth(80);
        dateColumn.setStyle("-fx-alignment: center;");	//居中
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<AqiFeedback, String> infoColumn = new TableColumn<>("反馈信息");
        infoColumn.setMinWidth(330);
        infoColumn.setCellValueFactory(new PropertyValueFactory<>("infomation"));

        txt_tableView.getColumns().addAll(afIdColumn, proviceNameColumn,cityNameColumn,estimateGradeColumn,dateColumn,infoColumn);
        ObservableList<AqiFeedback> data = FXCollections.observableArrayList();
        List<AqiFeedback> afList = new ArrayList<>();
        try {
            InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/aqi_feedback.json");
            afList = objectMapper.readValue(inputStream, new TypeReference<List<AqiFeedback>>() {
            });

            for (int i = afList.size() - 1; i >= 0; i--) {            //按照时间排序,有近到远
                AqiFeedback afb = afList.get(i);
                if (afb.getAfName().equals(supervisor.getRealName())) {
                    data.add(afb);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        txt_tableView.setItems(data);
    }
}

