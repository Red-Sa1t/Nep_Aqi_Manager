package com.nep.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.entity.AqiFeedback;
import com.nep.io.RWJsonTest;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import java.io.InputStream;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class NepmAqiInfoViewController implements Initializable {

    public static final ClassLoader classLoader = RWJsonTest.class.getClassLoader();
    public static final ObjectMapper objectMapper = new ObjectMapper();

    @FXML
    private MFXTableView<AqiFeedback> txt_tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
    }

    private void setupTable() {
        MFXTableColumn<AqiFeedback> afIdColumn = new MFXTableColumn<>("数据ID", true, Comparator.comparing(AqiFeedback::getAfId));
        MFXTableColumn<AqiFeedback> provinceNameColumn = new MFXTableColumn<>("省区域", Comparator.comparing(AqiFeedback::getProviceName));
        MFXTableColumn<AqiFeedback> cityNameColumn = new MFXTableColumn<>("市区域", Comparator.comparing(AqiFeedback::getCityName));
        MFXTableColumn<AqiFeedback> estimateGradeColumn = new MFXTableColumn<>("预估等级", Comparator.comparing(AqiFeedback::getEstimateGrade));
        MFXTableColumn<AqiFeedback> dateColumn = new MFXTableColumn<>("反馈时间", Comparator.comparing(AqiFeedback::getDate));
        MFXTableColumn<AqiFeedback> afNameColumn = new MFXTableColumn<>("反馈者", Comparator.comparing(AqiFeedback::getAfName));
        MFXTableColumn<AqiFeedback> infoColumn = new MFXTableColumn<>("反馈信息", Comparator.comparing(AqiFeedback::getInfomation));

        afIdColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getAfId));

        provinceNameColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getProviceName));
        provinceNameColumn.setAlignment(Pos.CENTER);

        cityNameColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getCityName));
        cityNameColumn.setAlignment(Pos.CENTER);

        estimateGradeColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getEstimateGrade));
        estimateGradeColumn.setAlignment(Pos.CENTER);

        dateColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getDate));
        dateColumn.setAlignment(Pos.CENTER);

        afNameColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getAfName));
        afNameColumn.setAlignment(Pos.CENTER);

        infoColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getInfomation));
        infoColumn.setAlignment(Pos.CENTER);

        txt_tableView.getTableColumns().addAll(
                afIdColumn,
                provinceNameColumn,
                cityNameColumn,
                estimateGradeColumn,
                dateColumn,
                afNameColumn,
                infoColumn
        );
//
//        // 添加列
//        txt_tableView.getTableColumns().addAll(afIdColumn, provinceNameColumn, cityNameColumn, afNameColumn, infoColumn);

        // 添加过滤器
        txt_tableView.getFilters().addAll(
                new IntegerFilter<>("数据ID", AqiFeedback::getAfId),
                new StringFilter<>("省区域", AqiFeedback::getProviceName),
                new StringFilter<>("市区域", AqiFeedback::getCityName),
                new StringFilter<>("测试等级", AqiFeedback::getEstimateGrade),
                new StringFilter<>("日期", AqiFeedback::getDate),
                new StringFilter<>("反馈者", AqiFeedback::getAfName),
                new StringFilter<>("反馈信息", AqiFeedback::getInfomation)
        );

        // 仅加载“未指派”的数据
        ObservableList<AqiFeedback> data = FXCollections.observableArrayList();
        try (InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/aqi_feedback.json")) {
            List<AqiFeedback> afList = objectMapper.readValue(inputStream, new TypeReference<List<AqiFeedback>>() {
            });
            for (AqiFeedback afb : afList) {
                if ("未指派".equals(afb.getState())) {
                    data.add(afb);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        txt_tableView.setItems(data);
    }
}
