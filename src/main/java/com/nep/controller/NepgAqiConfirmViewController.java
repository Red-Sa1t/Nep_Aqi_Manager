package com.nep.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nep.dto.AqiLimitDto;
import com.nep.entity.AqiFeedback;
import com.nep.entity.GridMember;
import com.nep.service.AqiFeedbackService;
import com.nep.service.impl.AqiFeedbackServiceImpl;
import com.nep.util.CommonUtil;
import com.nep.util.JavafxUtil;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.nep.controller.NepmConfirmInfoViewController.classLoader;
import static com.nep.controller.NepmConfirmInfoViewController.objectMapper;

public class NepgAqiConfirmViewController implements Initializable {
    @FXML
    private TableView<AqiFeedback> txt_tableView;
    @FXML
    private Pane txt_pane;
    @FXML
    private MFXTextField txt_afId;
    @FXML
    private MFXTextField txt_so2;
    @FXML
    private MFXTextField txt_co;
    @FXML
    private MFXTextField txt_pm;
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

    public static GridMember gridMember;  // 当前网格员信息
    public static Stage primaryStage;     // 主舞台

    // 多态实现业务逻辑
    private AqiFeedbackService aqiFeedbackService = new AqiFeedbackServiceImpl();

    private int so2level;
    private int colevel;
    private int pmlevel;
    private AqiLimitDto confirmDto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化pane样式
        if (txt_pane != null) {
            txt_pane.setStyle("-fx-border-color: #CCC;");
        }

        // 初始化网格员姓名
        if (label_realName != null && gridMember != null) {
            label_realName.setText(gridMember.getRealName());
        }

        // 初始化表格列
        initTableColumns();

        // 加载数据
        loadTableData();

        // 添加监听器
        addListeners();
    }

    private void initTableColumns() {
        TableColumn<AqiFeedback, Integer> afIdColumn = new TableColumn<>("编号");
        afIdColumn.setMinWidth(40);
        afIdColumn.setStyle("-fx-alignment: center;");
        afIdColumn.setCellValueFactory(new PropertyValueFactory<>("afId"));

        TableColumn<AqiFeedback, String> proviceNameColumn = new TableColumn<>("省区域");
        proviceNameColumn.setMinWidth(60);
        proviceNameColumn.setStyle("-fx-alignment: center;");
        proviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("proviceName"));

        TableColumn<AqiFeedback, String> cityNameColumn = new TableColumn<>("市区域");
        cityNameColumn.setMinWidth(60);
        cityNameColumn.setStyle("-fx-alignment: center;");
        cityNameColumn.setCellValueFactory(new PropertyValueFactory<>("cityName"));

        TableColumn<AqiFeedback, String> estimateGradeColumn = new TableColumn<>("预估等级");
        estimateGradeColumn.setMinWidth(60);
        estimateGradeColumn.setStyle("-fx-alignment: center;");
        estimateGradeColumn.setCellValueFactory(new PropertyValueFactory<>("estimateGrade"));

        TableColumn<AqiFeedback, String> dateColumn = new TableColumn<>("反馈时间");
        dateColumn.setMinWidth(80);
        dateColumn.setStyle("-fx-alignment: center;");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<AqiFeedback, String> afNameColumn = new TableColumn<>("反馈者");
        afNameColumn.setMinWidth(60);
        afNameColumn.setStyle("-fx-alignment: center;");
        afNameColumn.setCellValueFactory(new PropertyValueFactory<>("afName"));

        TableColumn<AqiFeedback, String> addressColumn = new TableColumn<>("具体地址");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<AqiFeedback, String> infoColumn = new TableColumn<>("反馈信息");
        infoColumn.setCellValueFactory(new PropertyValueFactory<>("infomation"));

        txt_tableView.getColumns().clear();
        txt_tableView.getColumns().addAll(afIdColumn, afNameColumn, dateColumn, estimateGradeColumn, proviceNameColumn, cityNameColumn, addressColumn, infoColumn);
    }

    private void loadTableData() {
        ObservableList<AqiFeedback> data = FXCollections.observableArrayList();
        List<AqiFeedback> afList = new ArrayList<>();
        try {
            InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/aqi_feedback.json");
            afList = objectMapper.readValue(inputStream, new TypeReference<List<AqiFeedback>>() {});
            for (AqiFeedback afb : afList) {
                if (afb.getGmName() != null && afb.getGmName().equals(gridMember.getRealName()) && "已指派".equals(afb.getState())) {
                    data.add(afb);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        txt_tableView.setItems(data);
    }

    private void addListeners() {
        List<AqiFeedback> finalAfList = txt_tableView.getItems();

        txt_afId.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // 失去焦点时校验
                boolean flag = true;
                for (AqiFeedback afb : finalAfList) {
                    if (afb.getGmName() != null && afb.getAfId().toString().equals(txt_afId.getText())) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    JavafxUtil.showAlert(primaryStage, "数据错误", "AQI反馈数据编号有误", "请重新输入AQI反馈数据编号", "warn");
                    txt_afId.setText("");
                }
            }
        });

        txt_so2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    double val = Double.parseDouble(newValue);
                    AqiLimitDto dto = CommonUtil.so2Limit(val);
                    updateLabelWithDto(label_so2level, label_so2explain, dto);
                    so2level = dto.getIntlevel();
                    updateConfirmLevel();
                } catch (NumberFormatException ignored) {}
            } else {
                resetSo2Labels();
                so2level = 0;
                updateConfirmLevel();
            }
        });

        txt_co.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    double val = Double.parseDouble(newValue);
                    AqiLimitDto dto = CommonUtil.coLimit(val);
                    updateLabelWithDto(label_colevel, label_coexplain, dto);
                    colevel = dto.getIntlevel();
                    updateConfirmLevel();
                } catch (NumberFormatException ignored) {}
            } else {
                resetCoLabels();
                colevel = 0;
                updateConfirmLevel();
            }
        });

        txt_pm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    double val = Double.parseDouble(newValue);
                    AqiLimitDto dto = CommonUtil.pmLimit(val);
                    updateLabelWithDto(label_pmlevel, label_pmexplain, dto);
                    pmlevel = dto.getIntlevel();
                    updateConfirmLevel();
                } catch (NumberFormatException ignored) {}
            } else {
                resetPmLabels();
                pmlevel = 0;
                updateConfirmLevel();
            }
        });
    }

    private void updateLabelWithDto(Label levelLabel, Label explainLabel, AqiLimitDto dto) {
        levelLabel.setText(dto.getLevel());
        levelLabel.setStyle("-fx-text-fill:" + dto.getColor() + ";");
        explainLabel.setText(dto.getExplain());
        explainLabel.setStyle("-fx-background-color:" + dto.getColor() + "; -fx-background-radius: 12; -fx-padding: 4 10; -fx-text-fill: white;");
    }

    private void resetSo2Labels() {
        label_so2level.setText("无");
        label_so2level.setStyle("-fx-text-fill:black;");
        label_so2explain.setText("");
        label_so2explain.setStyle("-fx-background-color:none;");
    }

    private void resetCoLabels() {
        label_colevel.setText("无");
        label_colevel.setStyle("-fx-text-fill:black;");
        label_coexplain.setText("");
        label_coexplain.setStyle("-fx-background-color:none;");
    }

    private void resetPmLabels() {
        label_pmlevel.setText("无");
        label_pmlevel.setStyle("-fx-text-fill:black;");
        label_pmexplain.setText("");
        label_pmexplain.setStyle("-fx-background-color:none;");
    }

    private void updateConfirmLevel() {
        confirmDto = CommonUtil.confirmLevel(so2level, colevel, pmlevel);
        label_confirmlevel.setText(confirmDto.getLevel());
        label_confirmlevel.setStyle("-fx-text-fill:" + confirmDto.getColor() + ";");
        label_confirmexplain.setText(confirmDto.getExplain());
        label_confirmexplain.setStyle("-fx-background-color:" + confirmDto.getColor() + "; -fx-background-radius: 12; -fx-padding: 4 10; -fx-text-fill: white;");
    }

    @FXML
    public void confirmData() {
        try {
            AqiFeedback afb = new AqiFeedback();
            afb.setAfId(Integer.parseInt(txt_afId.getText()));
            afb.setState("已实测");
            afb.setSo2(Double.parseDouble(txt_so2.getText()));
            afb.setCo(Double.parseDouble(txt_co.getText()));
            afb.setPm(Double.parseDouble(txt_pm.getText()));
            afb.setConfirmDate(CommonUtil.currentDate());
            afb.setConfirmLevel(confirmDto.getLevel());
            afb.setConfirmExplain(confirmDto.getExplain());
            afb.setGmName(gridMember.getRealName());

            aqiFeedbackService.confirmData(afb);

            JavafxUtil.showAlert(primaryStage, "提交成功", "污染物实测数据提交成功", "", "info");

            // 刷新表格数据
            loadTableData();

            // 重置输入框和标签
            reset();
        } catch (Exception e) {
            JavafxUtil.showAlert(primaryStage, "错误", "提交数据失败", e.getMessage(), "error");
        }
    }

    private void reset() {
        txt_afId.clear();
        txt_so2.clear();
        txt_co.clear();
        txt_pm.clear();

        resetSo2Labels();
        resetCoLabels();
        resetPmLabels();

        label_confirmlevel.setText("");
        label_confirmexplain.setText("");
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) txt_pane.getScene().getWindow();
        stage.close();
    }
}
