package com.nep.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.io.RWJsonTest;
import com.nep.manager.TipsManager;
import com.nep.po.AqiFinish;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class NepmConfirmInfoViewController implements Initializable {

    private final Stage stage;
    public MFXButton btnShowDetails;
    @FXML
    private MFXTableView<AqiFinish> txt_tableView;

    public static final ClassLoader classLoader = RWJsonTest.class.getClassLoader();
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public GridPane rootStackPane;
    MFXGenericDialog dialogContent;
    MFXStageDialog dialog;

    NepmConfirmInfoViewController(Stage stage) {
        this.stage = stage;
    }

    private void refreshTable() {
        ObservableList<AqiFinish> data = FXCollections.observableArrayList();
        try (InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/aqi_finish.json")) {
            List<AqiFinish> afList = objectMapper.readValue(inputStream, new TypeReference<List<AqiFinish>>() {
            });
            data.addAll(afList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        txt_tableView.setItems(data);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        btnShowDetails.setOnAction(event -> {
            AqiFinish selected = txt_tableView.getSelectionModel().getSelectedValue();
            if (selected != null) {
                showDetailsDialog(selected);
            } else {
                TipsManager.getInstance().showWarning("请先选择一条报告");
            }
        });
    }

    private void setupTable() {
        if (!txt_tableView.getTableColumns().isEmpty()) return;
        MFXTableColumn<AqiFinish> afIdColumn = new MFXTableColumn<>("编号", true, Comparator.comparing(AqiFinish::getAfId));
        MFXTableColumn<AqiFinish> provinceNameColumn = new MFXTableColumn<>("省区域", Comparator.comparing(AqiFinish::getProviceName));
        MFXTableColumn<AqiFinish> cityNameColumn = new MFXTableColumn<>("市区域", Comparator.comparing(AqiFinish::getCityName));
        MFXTableColumn<AqiFinish> estimateGradeColumn = new MFXTableColumn<>("预估等级", Comparator.comparing(AqiFinish::getEstimateGrade));
        MFXTableColumn<AqiFinish> dateColumn = new MFXTableColumn<>("反馈时间", Comparator.comparing(AqiFinish::getDate));
        MFXTableColumn<AqiFinish> afNameColumn = new MFXTableColumn<>("反馈者", Comparator.comparing(AqiFinish::getAfName));
        MFXTableColumn<AqiFinish> so2Column = new MFXTableColumn<>("SO2浓度(ug/m3)", Comparator.comparing(AqiFinish::getSo2));
        MFXTableColumn<AqiFinish> coColumn = new MFXTableColumn<>("CO浓度(ug/m3)", Comparator.comparing(AqiFinish::getCo));
        MFXTableColumn<AqiFinish> pmColumn = new MFXTableColumn<>("PM2.5浓度(ug/m3)", Comparator.comparing(AqiFinish::getPm));
        MFXTableColumn<AqiFinish> confirmLevelColumn = new MFXTableColumn<>("实测等级", Comparator.comparing(AqiFinish::getConfirmLevel));
        MFXTableColumn<AqiFinish> confirmExplainColumn = new MFXTableColumn<>("等级说明", Comparator.comparing(AqiFinish::getConfirmExplain));
        MFXTableColumn<AqiFinish> confirmDateColumn = new MFXTableColumn<>("实测日期", Comparator.comparing(AqiFinish::getConfirmDate));
        MFXTableColumn<AqiFinish> gmNameColumn = new MFXTableColumn<>("网格员", Comparator.comparing(AqiFinish::getGmName));
        MFXTableColumn<AqiFinish> informationColumn = new MFXTableColumn<>("详细信息", Comparator.comparing(AqiFinish::getInfomation));

        afIdColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFinish::getAfId));
        afIdColumn.setAlignment(Pos.CENTER);

        provinceNameColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFinish::getProviceName));
        provinceNameColumn.setAlignment(Pos.CENTER);

        cityNameColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFinish::getCityName));
        cityNameColumn.setAlignment(Pos.CENTER);

        estimateGradeColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFinish::getEstimateGrade));
        estimateGradeColumn.setAlignment(Pos.CENTER);

        informationColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFinish::getInfomation));
        informationColumn.setAlignment(Pos.CENTER);

        dateColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFinish::getDate));
        dateColumn.setAlignment(Pos.CENTER);

        afNameColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFinish::getAfName));
        afNameColumn.setAlignment(Pos.CENTER);

        so2Column.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFinish::getSo2));
        so2Column.setAlignment(Pos.CENTER);

        coColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFinish::getCo));
        coColumn.setAlignment(Pos.CENTER);

        pmColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFinish::getPm));
        pmColumn.setAlignment(Pos.CENTER);

        confirmLevelColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFinish::getConfirmLevel));
        confirmLevelColumn.setAlignment(Pos.CENTER);

        confirmExplainColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFinish::getConfirmExplain));
        confirmExplainColumn.setAlignment(Pos.CENTER);

        confirmDateColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFinish::getConfirmDate));
        confirmDateColumn.setAlignment(Pos.CENTER);

        gmNameColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFinish::getGmName));
        gmNameColumn.setAlignment(Pos.CENTER);

        txt_tableView.getTableColumns().addAll(
                afIdColumn,
                provinceNameColumn,
                cityNameColumn,
                estimateGradeColumn,
                informationColumn,
                dateColumn,
                afNameColumn,
                so2Column,
                coColumn,
                pmColumn,
                confirmLevelColumn,
                confirmExplainColumn,
                confirmDateColumn,
                gmNameColumn
        );

        txt_tableView.setPrefWidth(8);  // 设置表格宽度，比如800像素，小于所有列宽总和

        // 添加过滤器，比如编号为整数过滤，其他列可添加字符串过滤器
        txt_tableView.getFilters().addAll(
                new IntegerFilter<>("编号", AqiFinish::getAfId)
        );

        refreshTable();
    }

    private void showDetailsDialog(AqiFinish finish) {
        String content = String.format(
                "反馈ID: %d\n反馈人: %s\n省份: %s\n城市: %s\n地址: %s\n信息描述: %s\n估计等级: %s\n反馈日期: %s\n状态: %s\n管理员: %s\n" +
                        "确认日期: %s\nSO₂浓度: %.1f（等级%d）\nCO浓度: %.1f（等级%d）\nPM浓度: %.1f（等级%d）\n确认等级: %s\n说明: %s",
                finish.getAfId(),
                finish.getAfName(),
                finish.getProviceName(),
                finish.getCityName(),
                finish.getAddress(),
                finish.getInfomation(),
                finish.getEstimateGrade(),
                finish.getDate(),
                finish.getState(),
                finish.getGmName(),
                finish.getConfirmDate(),
                finish.getSo2(), finish.getSo2_level(),
                finish.getCo(), finish.getCo_level(),
                finish.getPm(), finish.getPm_level(),
                finish.getConfirmLevel(),
                finish.getConfirmExplain()
        );

        dialogContent = MFXGenericDialogBuilder.build()
                .setContentText(content)
                .makeScrollable(true)
                .get();
        dialogContent.setHeaderText("报告详细信息");
        dialogContent.setContentText(content);
        dialogContent.setMaxWidth(400);
        dialogContent.setPadding(new Insets(20));
        dialogContent.setShowClose(true);

        dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setTitle("报告详细信息")
                .setOwnerNode(rootStackPane)
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();
        MFXFontIcon infoIcon = new MFXFontIcon("fas-circle-info", 18);
        dialogContent.setHeaderIcon(infoIcon);
        dialogContent.setHeaderText("报告详细信息");
        dialog.showDialog();
    }
}
