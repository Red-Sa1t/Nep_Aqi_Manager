package com.nep.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.po.AqiFeedback;
import com.nep.po.Supervisor;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

import java.io.InputStream;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class NepsFeedbackViewController implements Initializable {

    public static NepsFeedbackViewController instance;

    public static final ClassLoader classLoader = NepsFeedbackViewController.class.getClassLoader();

    @FXML
    private Label txt_realName;
    public static final ObjectMapper objectMapper = new ObjectMapper();
    // 主舞台和用户对象（需在主程序中赋值）
    public static Supervisor supervisor;
    @FXML
    private MFXTableView<AqiFeedback> txt_tableView;

    public MFXTableView<AqiFeedback> getTxt_tableView() {
        return txt_tableView;
    }

    public void setTxt_tableView(MFXTableView<AqiFeedback> txt_tableView) {
        this.txt_tableView = txt_tableView;
    }

    public Label getTxt_realName() {
        return txt_realName;
    }

    public void setTxt_realName(Label txt_realName) {
        this.txt_realName = txt_realName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NepsFeedbackViewController.instance = this;
        // 初始化用户姓名显示
        txt_realName.setText(supervisor.getRealName());

        // 表格列定义与工厂绑定
        MFXTableColumn<AqiFeedback> afIdColumn = new MFXTableColumn<>("编号", true, Comparator.comparing(AqiFeedback::getAfId));
        afIdColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getAfId));
        afIdColumn.setAlignment(Pos.CENTER);

        MFXTableColumn<AqiFeedback> proviceNameColumn = new MFXTableColumn<>("省区域", Comparator.comparing(AqiFeedback::getProviceName));
        proviceNameColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getProviceName));
        proviceNameColumn.setAlignment(Pos.CENTER);

        MFXTableColumn<AqiFeedback> cityNameColumn = new MFXTableColumn<>("市区域", Comparator.comparing(AqiFeedback::getCityName));
        cityNameColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getCityName));
        cityNameColumn.setAlignment(Pos.CENTER);

        MFXTableColumn<AqiFeedback> estimateGradeColumn = new MFXTableColumn<>("预估等级", Comparator.comparing(AqiFeedback::getEstimateGrade));
        estimateGradeColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getEstimateGrade));
        estimateGradeColumn.setAlignment(Pos.CENTER);

        MFXTableColumn<AqiFeedback> dateColumn = new MFXTableColumn<>("反馈时间", Comparator.comparing(AqiFeedback::getDate));
        dateColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getDate));
        dateColumn.setAlignment(Pos.CENTER);

        MFXTableColumn<AqiFeedback> infoColumn = new MFXTableColumn<>("反馈信息", Comparator.comparing(AqiFeedback::getInfomation));
        infoColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getInfomation));
        infoColumn.setAlignment(Pos.CENTER_LEFT);

        // 添加列到表格
        txt_tableView.getTableColumns().addAll(
                afIdColumn,
                proviceNameColumn,
                cityNameColumn,
                estimateGradeColumn,
                dateColumn,
                infoColumn
        );

        // 过滤器
        txt_tableView.getFilters().addAll(
                new IntegerFilter<>("编号", AqiFeedback::getAfId)
        );

        RefreshTable();
    }

    public void RefreshTable() {
        ObservableList<AqiFeedback> data = FXCollections.observableArrayList();
        try (InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/aqi_feedback.json")) {
            List<AqiFeedback> afList = objectMapper.readValue(inputStream, new TypeReference<List<AqiFeedback>>() {
            });

            List<AqiFeedback> userFeedback = afList.stream()
                    .filter(af -> af.getAfName().equals(supervisor.getRealName()))
                    .sorted((a, b) -> b.getDate().compareTo(a.getDate())) // 时间倒序
                    .collect(Collectors.toList());

            data.addAll(userFeedback);
        } catch (Exception e) {
            e.printStackTrace();
        }

        txt_tableView.setItems(data);
    }
}
