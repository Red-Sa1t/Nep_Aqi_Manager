package com.nep.controller;

import com.nep.po.AqiFeedback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
//import com.nep.entity.AqiFeedback;
import com.nep.util.FileUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NepmAqiInfoViewController implements Initializable {

    @FXML
    private TableView<AqiFeedback> txt_tableView;

    public TableView<AqiFeedback> getTxt_tableView() {
        return txt_tableView;
    }

    public void setTxt_tableView(TableView<AqiFeedback> txt_tableView) {
        this.txt_tableView = txt_tableView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
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

        TableColumn<AqiFeedback, String> afNameColumn = new TableColumn<>("反馈者");
        afNameColumn.setMinWidth(60);
        afNameColumn.setStyle("-fx-alignment: center;");	//居中
        afNameColumn.setCellValueFactory(new PropertyValueFactory<>("afName"));

        TableColumn<AqiFeedback, String> infoColumn = new TableColumn<>("反馈信息");
        infoColumn.setMinWidth(210);
        infoColumn.setCellValueFactory(new PropertyValueFactory<>("infomation"));

        txt_tableView.getColumns().addAll(afIdColumn, proviceNameColumn,cityNameColumn,estimateGradeColumn,dateColumn,afNameColumn,infoColumn);
        ObservableList<AqiFeedback> data = FXCollections.observableArrayList();
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<AqiFeedback> afList = (List<AqiFeedback>) FileUtil.readObject(ProPaht+"aqifeedback.txt");
        for(AqiFeedback afb:afList){
            if(afb.getState().equals("未指派")){
                data.add(afb);
            }

        }
        txt_tableView.setItems(data);
    }

}
