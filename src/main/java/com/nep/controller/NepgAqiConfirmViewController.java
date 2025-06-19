package com.nep.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nep.dto.AqiLimitDto;
import com.nep.manager.TipsManager;
import com.nep.po.AqiFeedback;
import com.nep.po.AqiFinish;
import com.nep.po.GridMember;
import com.nep.service.AqiFeedbackService;
import com.nep.service.AqiFinishService;
import com.nep.service.impl.AqiFeedbackServiceImpl;
import com.nep.service.impl.AqiFinishServiceImpl;
import com.nep.util.CommonUtil;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import static com.nep.controller.NepmConfirmInfoViewController.classLoader;
import static com.nep.controller.NepmConfirmInfoViewController.objectMapper;

public class NepgAqiConfirmViewController implements Initializable {

    public static GridMember gridMember;
    @FXML
    private Pane txt_pane;
    public static Stage primaryStage;
    private final AqiFeedbackService aqiFeedbackService = new AqiFeedbackServiceImpl();
    private final AqiFinishService aqiFinishService = new AqiFinishServiceImpl();
    @FXML
    private MFXTableView<AqiFeedback> txt_tableView;
    @FXML
    private MFXTextField txt_afId, txt_so2, txt_co, txt_pm;
    @FXML
    private Label label_so2level, label_so2explain, label_colevel, label_coexplain;
    @FXML
    private Label label_pmlevel, label_pmexplain, label_confirmlevel, label_confirmexplain, label_realName;
    private int so2level, colevel, pmlevel;
    private AqiLimitDto confirmDto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (txt_pane != null) txt_pane.setStyle("-fx-border-color: #CCC;");
        if (label_realName != null && gridMember != null) label_realName.setText(gridMember.getRealName());
        initTableColumns();
        loadTableData();
        addListeners();
    }

    private void initTableColumns() {
        MFXTableColumn<AqiFeedback> afIdCol = new MFXTableColumn<>("编号", true, Comparator.comparing(AqiFeedback::getAfId));
        afIdCol.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getAfId));
        afIdCol.setAlignment(Pos.CENTER);

        MFXTableColumn<AqiFeedback> provinceCol = new MFXTableColumn<>("省区域", Comparator.comparing(AqiFeedback::getProviceName));
        provinceCol.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getProviceName));
        provinceCol.setAlignment(Pos.CENTER);

        MFXTableColumn<AqiFeedback> cityCol = new MFXTableColumn<>("市区域", Comparator.comparing(AqiFeedback::getCityName));
        cityCol.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getCityName));
        cityCol.setAlignment(Pos.CENTER);

        MFXTableColumn<AqiFeedback> gradeCol = new MFXTableColumn<>("预估等级", Comparator.comparing(AqiFeedback::getEstimateGrade));
        gradeCol.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getEstimateGrade));
        gradeCol.setAlignment(Pos.CENTER);

        MFXTableColumn<AqiFeedback> dateCol = new MFXTableColumn<>("反馈时间", Comparator.comparing(AqiFeedback::getDate));
        dateCol.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getDate));
        dateCol.setAlignment(Pos.CENTER);

        MFXTableColumn<AqiFeedback> nameCol = new MFXTableColumn<>("反馈者", Comparator.comparing(AqiFeedback::getAfName));
        nameCol.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getAfName));
        nameCol.setAlignment(Pos.CENTER);

        MFXTableColumn<AqiFeedback> addressCol = new MFXTableColumn<>("具体地址", Comparator.comparing(AqiFeedback::getAddress));
        addressCol.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getAddress));
        addressCol.setAlignment(Pos.CENTER);

        MFXTableColumn<AqiFeedback> infoCol = new MFXTableColumn<>("反馈信息", Comparator.comparing(AqiFeedback::getInfomation));
        infoCol.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getInfomation));
        infoCol.setAlignment(Pos.CENTER);

        txt_tableView.getTableColumns().addAll(afIdCol, nameCol, dateCol, gradeCol, provinceCol, cityCol, addressCol, infoCol);
        txt_tableView.getFilters().add(new IntegerFilter<>("编号", AqiFeedback::getAfId));
    }

    private void loadTableData() {
        ObservableList<AqiFeedback> data = FXCollections.observableArrayList();
        try (InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/aqi_feedback.json")) {
            List<AqiFeedback> afList = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            afList.stream()
                    .filter(afb -> afb.getGmName() != null && afb.getGmName().equals(gridMember.getRealName()) && "已指派".equals(afb.getState()))
                    .forEach(data::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        txt_tableView.setItems(data);
    }

    private void addListeners() {
        txt_afId.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                boolean valid = txt_tableView.getItems().stream().anyMatch(afb -> afb.getAfId().toString().equals(txt_afId.getText()));
                if (!valid) {
                    TipsManager.getInstance().showError("AQI反馈数据编号有误");
                    txt_afId.setText("");
                }
            }
        });

        addListener(txt_so2, CommonUtil::so2Limit, label_so2level, label_so2explain, val -> so2level = val);
        addListener(txt_co, CommonUtil::coLimit, label_colevel, label_coexplain, val -> colevel = val);
        addListener(txt_pm, CommonUtil::pmLimit, label_pmlevel, label_pmexplain, val -> pmlevel = val);
    }

    private void addListener(MFXTextField field, java.util.function.Function<Double, AqiLimitDto> func, Label levelLabel, Label explainLabel, java.util.function.IntConsumer setLevel) {
        field.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                try {
                    double val = Double.parseDouble(newVal);
                    AqiLimitDto dto = func.apply(val);
                    updateLabelWithDto(levelLabel, explainLabel, dto);
                    setLevel.accept(dto.getIntlevel());
                    updateConfirmLevel();
                } catch (NumberFormatException ignored) {}
            } else {
                levelLabel.setText("无");
                levelLabel.setStyle("-fx-text-fill:black;");
                explainLabel.setText("");
                explainLabel.setStyle("-fx-background-color:none;");
                setLevel.accept(0);
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

    private void updateConfirmLevel() {
        confirmDto = CommonUtil.confirmLevel(so2level, colevel, pmlevel);
        if (confirmDto != null) {
            label_confirmlevel.setText(confirmDto.getLevel());
            label_confirmlevel.setStyle("-fx-text-fill:" + confirmDto.getColor() + ";");
            label_confirmexplain.setText(confirmDto.getExplain());
            label_confirmexplain.setStyle("-fx-background-color:" + confirmDto.getColor() + "; -fx-background-radius: 12; -fx-padding: 4 10; -fx-text-fill: white;");
        }
    }

    @FXML
    public void confirmData() {
        try {
            if (txt_afId.getText().isEmpty() ||
                    txt_so2.getText().isEmpty() ||
                    txt_co.getText().isEmpty() ||
                    txt_pm.getText().isEmpty()) {
                TipsManager.getInstance().showError("请填写所有污染物数据和反馈编号");
                return;
            }

            AqiFeedback data = null;
            try (InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/aqi_feedback.json")) {
                List<AqiFeedback> afList = objectMapper.readValue(inputStream, new TypeReference<>() {
                });
                for (AqiFeedback afb : afList) {
                    if (afb.getGmName() != null && afb.getGmName().equals(gridMember.getRealName()) && "已指派".equals(afb.getState())) {
                        data = afb;
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (data == null) {
                TipsManager.getInstance().showWarning("未找到对应的Aqi反馈");
                return;
            }
            AqiFinish afb = new AqiFinish();
            afb.setAfName(data.getAfName());
            afb.setProviceName(data.getProviceName());
            afb.setCityName(data.getCityName());
            afb.setAddress(data.getAddress());
            afb.setInfomation(data.getInfomation());
            afb.setEstimateGrade(data.getEstimateGrade());
            afb.setDate(data.getDate());
            afb.setAfId(Integer.parseInt(txt_afId.getText()));
            afb.setState("已实测");
            afb.setSo2(Double.parseDouble(txt_so2.getText()));
            afb.setCo(Double.parseDouble(txt_co.getText()));
            afb.setPm(Double.parseDouble(txt_pm.getText()));
            afb.setConfirmDate(CommonUtil.currentDate());
            afb.setConfirmLevel(confirmDto.getLevel());
            afb.setConfirmExplain(confirmDto.getExplain());
            afb.setGmName(gridMember.getRealName());
            afb.setSo2_level(so2level);
            afb.setCo_level(colevel);
            afb.setPm_level(pmlevel);

            aqiFinishService.confirmData(afb);
            TipsManager.getInstance().showInfo("Aqi实测数据提交成功");
            loadTableData();
            reset();
        } catch (Exception e) {
            TipsManager.getInstance().showError("提交数据失败");
        }
    }

    private void reset() {
        txt_afId.clear();
        txt_so2.clear();
        txt_co.clear();
        txt_pm.clear();
        label_so2level.setText("无");
        label_so2level.setStyle("-fx-text-fill:black;");
        label_so2explain.setText("");
        label_colevel.setText("无");
        label_colevel.setStyle("-fx-text-fill:black;");
        label_coexplain.setText("");
        label_pmlevel.setText("无");
        label_pmlevel.setStyle("-fx-text-fill:black;");
        label_pmexplain.setText("");
        label_confirmlevel.setText("");
        label_confirmexplain.setText("");
    }

    public void handleEnterKey(KeyEvent keyEvent) {
        confirmData();
    }
}