package com.nep.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.io.RWJsonTest;
import com.nep.manager.TipsManager;
import com.nep.po.Aqi;
import com.nep.po.AqiFeedback;
import com.nep.po.ProvinceCity;
import com.nep.po.Supervisor;
import com.nep.service.AqiFeedbackService;
import com.nep.service.impl.AqiFeedbackServiceImpl;
import com.nep.util.CommonUtil;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NepsSelectAqiViewController implements Initializable {
    private final AqiFeedbackService aqiFeedbackService = new AqiFeedbackServiceImpl();
    @FXML
    private MFXTableView<Aqi> txt_tableView;
    @FXML
    private MFXComboBox<String> txt_province;
    @FXML
    private MFXComboBox<String> txt_city;
    @FXML
    private MFXTextField txt_address;
    @FXML
    private TextArea txt_information;
    @FXML
    private Label label_realName;

    public static Stage primaryStage;
    public static Supervisor supervisor;
    public static ClassLoader classLoader = RWJsonTest.class.getClassLoader();
    public static ObjectMapper objectMapper = new ObjectMapper();
    @FXML
    private MFXComboBox<String> txt_level;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label_realName.setText(supervisor.getRealName());
        initTableView();
        initLevelComboBox();
        initProvinceCityComboBox();
    }

    private void initTableView() {
        MFXTableColumn<Aqi> levelColumn = new MFXTableColumn<>("级别", false);
        levelColumn.setRowCellFactory(aqi -> new MFXTableRowCell<>(Aqi::getLevel));

        MFXTableColumn<Aqi> explainColumn = new MFXTableColumn<>("说明", false);
        explainColumn.setRowCellFactory(aqi -> new MFXTableRowCell<>(Aqi::getExplain));

        MFXTableColumn<Aqi> impactColumn = new MFXTableColumn<>("对健康影响", false);
        impactColumn.setMinWidth(400);
        impactColumn.setRowCellFactory(aqi -> new MFXTableRowCell<>(Aqi::getImpact));

        txt_tableView.getTableColumns().addAll(levelColumn, explainColumn, impactColumn);

        try (InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/aqi.json")) {
            List<Aqi> alist = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            txt_tableView.setItems(FXCollections.observableArrayList(alist));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLevelComboBox() {
        try (InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/aqi.json")) {
            List<Aqi> alist = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            for (Aqi aqi : alist) {
                txt_level.getItems().add(aqi.getLevel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        txt_level.setValue("默认等级");
    }

    private void initProvinceCityComboBox() {
        List<ProvinceCity> plist;
        try (InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/province_city.json")) {
            plist = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        for (ProvinceCity province : plist) {
            txt_province.getItems().add(province.getProvinceName());
        }
        txt_province.setValue("默认省");
        txt_city.setValue("默认市");

        txt_province.valueProperty().addListener((observable, oldValue, newValue) -> {
            txt_city.getItems().clear();
            plist.stream()
                    .filter(p -> newValue.equals(p.getProvinceName()))
                    .findFirst()
                    .ifPresent(province -> {
                        txt_city.getItems().addAll(province.getCityName());
                        txt_city.setValue("默认市");
                    });
        });
    }

    @FXML
    public void saveFeedBack() {
        AqiFeedback afb = new AqiFeedback();
        afb.setAddress(txt_address.getText());
        afb.setAfName(supervisor.getRealName());
        afb.setProviceName(txt_province.getValue());
        afb.setCityName(txt_city.getValue());
        afb.setEstimateGrade(txt_level.getValue());
        afb.setInfomation(txt_information.getText());
        afb.setDate(CommonUtil.currentDate());
        afb.setState("未指派");
        afb.setGmName("无");

        aqiFeedbackService.saveFeedBack(afb);
        TipsManager.getInstance().showInfo("信息反馈成功！");
    }
}