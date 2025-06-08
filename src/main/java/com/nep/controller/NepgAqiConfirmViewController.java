package com.nep.controller;

import com.nep.dto.AqiLimitDto;
//import com.nep.entity.AqiFeedback;
//import com.nep.entity.GridMember;
import com.nep.po.AqiFeedback;
import com.nep.po.GridMember;
import com.nep.util.FileUtil;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NepgAqiConfirmViewController implements Initializable {
    @FXML
    private TableView<AqiFeedback> txt_tableView;
    @FXML
    private Pane txt_pane;
    @FXML
    private TextField txt_afId;
    @FXML
    private TextField txt_so2;
    @FXML
    private TextField txt_co;
    @FXML
    private TextField txt_pm;
    @FXML
    private Label label_so2level;
    @FXML
    private Label label_so2explain;
    @FXML
    private Label label_colevel;
    @FXML
    private Label label_coexplain;
    @FXML
    private Label label_pmlevel;
    @FXML
    private Label label_pmexplain;
    @FXML
    private Label label_confirmlevel;
    @FXML
    private Label label_confirmexplain;
    @FXML
    private Label label_realName;
    public static GridMember gridMember;	//当前网格员信息
    public static Stage primaryStage;		//主舞台
    //多态
    private AqiFeedbackService aqiFeedbackService = new AqiFeedbackServiceImpl();
    private int so2level;
    private int colevel;
    private int pmlevel;
    private AqiLimitDto confirmDto;
    public TableView<AqiFeedback> getTxt_tableView() {
        return txt_tableView;
    }

    public void setTxt_tableView(TableView<AqiFeedback> txt_tableView) {
        this.txt_tableView = txt_tableView;
    }

    public Pane getTxt_pane() {
        return txt_pane;
    }

    public void setTxt_pane(Pane txt_pane) {
        this.txt_pane = txt_pane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //初始化pane容器样式
        txt_pane.setStyle("-fx-border-color: #CCC;");
        // 初始化网格员姓名
        label_realName.setText(gridMember.getGmName());
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

        TableColumn<AqiFeedback, String> addressColumn = new TableColumn<>("具体地址");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<AqiFeedback, String> infoColumn = new TableColumn<>("反馈信息");
        infoColumn.setCellValueFactory(new PropertyValueFactory<>("infomation"));

        txt_tableView.getColumns().addAll(afIdColumn,afNameColumn,dateColumn,estimateGradeColumn, proviceNameColumn,cityNameColumn,addressColumn,infoColumn);
        ObservableList<AqiFeedback> data = FXCollections.observableArrayList();
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";

        List<AqiFeedback> afList = (List<AqiFeedback>) FileUtil.readObject(ProPaht+"aqifeedback.txt");
        for(AqiFeedback afb:afList){
            if(afb.getGmId() != null && afb.getGmName().equals(gridMember.getGmName()) && afb.getState().equals("已指派")){
                data.add(afb);
            }
        }
        txt_tableView.setItems(data);
        //添加编号文本框事件监听
        txt_afId.focusedProperty().addListener((obs,wasFocused,isNowFocused)->{
            if(!isNowFocused){
                boolean flag = true;
                for(AqiFeedback afb:afList){
                    if(afb.getGmName() != null && afb.getAfId().toString().equals(txt_afId.getText()) ){
                        flag = false;
                        return;
                    }
                }
                if(flag){
                    JavafxUtil.showAlert(primaryStage, "数据错误", "AQI反馈数据编号有误", "请重新输入AQI反馈数据编号","warn");
                    txt_afId.setText("");
                }
            }
        });
        txt_so2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // TODO Auto-generated method stub
                if(!txt_so2.getText().equals("")){
                    AqiLimitDto dto = CommonUtil.so2Limit(Double.parseDouble(txt_so2.getText()));
                    label_so2level.setText(dto.getLevel());
                    label_so2level.setStyle("-fx-text-fill:"+dto.getColor()+";");
                    label_so2explain.setText(dto.getExplain());
                    label_so2explain.setStyle("-fx-background-color:"+dto.getColor()+";");
                    so2level = dto.getIntlevel();
                    confirmDto = CommonUtil.confirmLevel(so2level, colevel, pmlevel);
                    label_confirmlevel.setText(confirmDto.getLevel());
                    label_confirmlevel.setStyle("-fx-text-fill:"+confirmDto.getColor()+";");
                    label_confirmexplain.setText(confirmDto.getExplain());
                    label_confirmexplain.setStyle("-fx-background-color:"+confirmDto.getColor()+";");
                }
            }

        });

        txt_co.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // TODO Auto-generated method stub
                if(!txt_co.getText().equals("")){
                    AqiLimitDto dto = CommonUtil.coLimit(Double.parseDouble(txt_co.getText()));
                    label_colevel.setText(dto.getLevel());
                    label_colevel.setStyle("-fx-text-fill:"+dto.getColor()+";");
                    label_coexplain.setText(dto.getExplain());
                    label_coexplain.setStyle("-fx-background-color:"+dto.getColor()+";");
                    colevel = dto.getIntlevel();
                    confirmDto = CommonUtil.confirmLevel(so2level, colevel, pmlevel);
                    label_confirmlevel.setText(confirmDto.getLevel());
                    label_confirmlevel.setStyle("-fx-text-fill:"+confirmDto.getColor()+";");
                    label_confirmexplain.setText(confirmDto.getExplain());
                    label_confirmexplain.setStyle("-fx-background-color:"+confirmDto.getColor()+";");
                }
            }

        });
        txt_pm.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // TODO Auto-generated method stub
                if(!txt_pm.getText().equals("")){
                    AqiLimitDto dto = CommonUtil.pmLimit(Double.parseDouble(txt_pm.getText()));
                    label_pmlevel.setText(dto.getLevel());
                    label_pmlevel.setStyle("-fx-text-fill:"+dto.getColor()+";");
                    label_pmexplain.setText(dto.getExplain());
                    label_pmexplain.setStyle("-fx-background-color:"+dto.getColor()+";");
                    pmlevel = dto.getIntlevel();
                    confirmDto = CommonUtil.confirmLevel(so2level, colevel, pmlevel);
                    label_confirmlevel.setText(confirmDto.getLevel());
                    label_confirmlevel.setStyle("-fx-text-fill:"+confirmDto.getColor()+";");
                    label_confirmexplain.setText(confirmDto.getExplain());
                    label_confirmexplain.setStyle("-fx-background-color:"+confirmDto.getColor()+";");
                }
            }

        });

    }

    public void confirmData(){
        AqiFeedback afb = new AqiFeedback();
        afb.setAfId(Integer.parseInt(txt_afId.getText()));
        afb.setState("已实测");
        afb.setSo2(Double.parseDouble(txt_so2.getText()));
        afb.setCo(Double.parseDouble(txt_co.getText()));
        afb.setPm(Double.parseDouble(txt_pm.getText()));
        afb.setConfirmDate(CommonUtil.currentDate());
        afb.setConfirmLevel(confirmDto.getLevel());
        afb.setConfirmExplain(confirmDto.getExplain());
        afb.setGmName(gridMember.getGmName());
        aqiFeedbackService.confirmData(afb);
        JavafxUtil.showAlert(primaryStage, "提交成功", "污染物实测数据提交成功", "","info");
        //刷新页面数据表格
        ObservableList<AqiFeedback> data = FXCollections.observableArrayList();
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<AqiFeedback> aList = (List<AqiFeedback>)FileUtil.readObject(ProPaht+"aqifeedback.txt");
        for(AqiFeedback a:aList){
            if(a.getGmName() != null && a.getGmName().equals(gridMember.getGmName()) && a.getState().equals("已指派")){
                data.add(a);
            }
        }
        txt_tableView.setItems(data);
        reset();
    }

    /**
     * 文本框和标签内容重置
     */
    public void reset(){
        txt_afId.setText("");
        txt_so2.setText("");
        txt_co.setText("");
        txt_pm.setText("");
        label_so2explain.setText("");
        label_so2explain.setStyle("-fx-background-color:none;");
        label_coexplain.setText("");
        label_coexplain.setStyle("-fx-background-color:none;");
        label_pmexplain.setText("");
        label_pmexplain.setStyle("-fx-background-color:none;");
        label_confirmexplain.setText("");
        label_confirmexplain.setStyle("-fx-background-color:none;");
        label_so2level.setText("无");
        label_so2level.setStyle("-fx-text-fill:black;");
        label_colevel.setText("无");
        label_colevel.setStyle("-fx-text-fill:black;");
        label_pmlevel.setText("无");
        label_pmlevel.setStyle("-fx-text-fill:black;");
        label_confirmlevel.setText("无");
        label_confirmlevel.setStyle("-fx-text-fill:black;");
    }
}
