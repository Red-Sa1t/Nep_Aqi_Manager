package com.nep.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.io.RWJsonTest;
import com.nep.manager.TipsManager;
import com.nep.po.AqiFeedback;
import com.nep.po.GridMember;
import com.nep.service.impl.AqiFeedbackServiceImpl;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
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
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class NepmAqiInfoViewController implements Initializable {
    public static NepmAqiInfoViewController instance;

    public static final ClassLoader classLoader = RWJsonTest.class.getClassLoader();
    public static final ObjectMapper objectMapper = new ObjectMapper();
    @FXML
    public GridPane rootStackPane;
    public MFXButton btnShowDetails;
    MFXGenericDialog dialogContent;
    MFXStageDialog dialog;
    private final Stage stage;

    NepmAqiInfoViewController(Stage stage) {
        this.stage = stage;
    }

    public static NepmAqiInfoViewController getInstance() {
        return instance;
    }

    @FXML
    private MFXTableView<AqiFeedback> txt_tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NepmAqiInfoViewController.instance = this;
        setupTable();
        RefreshTable();
        btnShowDetails.setOnAction(event -> {
            AqiFeedback selected = txt_tableView.getSelectionModel().getSelectedValue();
            if (selected != null) {
                showDetailsDialog(selected);
            } else {
                TipsManager.getInstance().showWarning("请先选择一条报告");
            }
        });
    }

    public void showAssignDialog(AqiFeedback afb) {
        // 创建根容器 StackPane
        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(20));
        stackPane.setPrefSize(350, 200);
        stackPane.setStyle("-fx-background-color: white;"); // 方便观察

        // 内容布局 VBox，方便垂直排列控件
        VBox vbox = new VBox(15);

        Label label = new Label("为反馈编号 " + afb.getAfId() + " 指派网格员：");

        MFXComboBox<String> comboBox = new MFXComboBox<>();
        comboBox.setPromptText("请选择网格员");
        try (InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/grid_member.json")) {
            List<GridMember> glist = objectMapper.readValue(inputStream, new TypeReference<List<GridMember>>() {
            });
            for (GridMember gm : glist) {
                if ("工作中".equals(gm.getState())) {
                    comboBox.getItems().add(gm.getRealName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        MFXButton confirmBtn = new MFXButton("确认指派");
        confirmBtn.setStyle("""
                -fx-background-color: -mfx-purple;
                -fx-text-fill: white""");
        confirmBtn.setOnAction(e -> {
            String selected = comboBox.getValue();
            if (selected == null || selected.isEmpty()) {
                TipsManager.getInstance().showWarning("请选择一个网格员");
                return;
            }
            new AqiFeedbackServiceImpl().assignGridMember(afb.getAfId().toString(), selected);
            TipsManager.getInstance().showInfo("网格员指派成功");
            dialog.close();
            RefreshTable();
        });


        vbox.getChildren().addAll(label, comboBox, confirmBtn);
        stackPane.getChildren().add(vbox);

        MFXGenericDialog dialogContent = MFXGenericDialogBuilder.build()
                .setHeaderText("指派网格员")
                .setHeaderIcon(new MFXFontIcon("fas-user-check", 18))
                .setShowClose(true)
                .get();

        dialogContent.setContent(stackPane);

        dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setTitle("指派网格员")
                .setOwnerNode(rootStackPane)
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();

        dialog.showDialog();

        RefreshTable();
    }

    private void setupTable() {
        if (!txt_tableView.getTableColumns().isEmpty()) return;
        MFXTableColumn<AqiFeedback> afIdColumn = new MFXTableColumn<>("数据ID", true, Comparator.comparing(AqiFeedback::getAfId));
        MFXTableColumn<AqiFeedback> provinceNameColumn = new MFXTableColumn<>("省区域", Comparator.comparing(AqiFeedback::getProviceName));
        MFXTableColumn<AqiFeedback> cityNameColumn = new MFXTableColumn<>("市区域", Comparator.comparing(AqiFeedback::getCityName));
        MFXTableColumn<AqiFeedback> estimateGradeColumn = new MFXTableColumn<>("预估等级", Comparator.comparing(AqiFeedback::getEstimateGrade));
        MFXTableColumn<AqiFeedback> dateColumn = new MFXTableColumn<>("反馈时间", Comparator.comparing(AqiFeedback::getDate));
        MFXTableColumn<AqiFeedback> afNameColumn = new MFXTableColumn<>("反馈者", Comparator.comparing(AqiFeedback::getAfName));
        MFXTableColumn<AqiFeedback> infoColumn = new MFXTableColumn<>("反馈信息", Comparator.comparing(AqiFeedback::getInfomation));
        MFXTableColumn<AqiFeedback> stateColumn = new MFXTableColumn<>("指派状态", Comparator.comparing(AqiFeedback::getState));
        MFXTableColumn<AqiFeedback> gmNameColumn = new MFXTableColumn<>("指派网格员", Comparator.comparing(AqiFeedback::getGmName));

        afIdColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getAfId));
        afIdColumn.setAlignment(Pos.CENTER);

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

        stateColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getState));
        stateColumn.setAlignment(Pos.CENTER);

        gmNameColumn.setRowCellFactory(item -> new MFXTableRowCell<>(AqiFeedback::getGmName));
        gmNameColumn.setAlignment(Pos.CENTER);

        MFXTableColumn<AqiFeedback> assignColumn = new MFXTableColumn<>("操作");

        assignColumn.setRowCellFactory(af -> {
            MFXTableRowCell<AqiFeedback, String> cell = new MFXTableRowCell<>(x -> "");
            MFXButton assignButton = new MFXButton("指派");

            // 设置点击事件
            assignButton.setOnAction(event -> showAssignDialog(af));
            assignButton.setId("custom_M");
            // 替换显示为按钮
            cell.setGraphic(assignButton);
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        txt_tableView.getTableColumns().addAll(
                afIdColumn,
                provinceNameColumn,
                cityNameColumn,
                estimateGradeColumn,
                dateColumn,
                afNameColumn,
                infoColumn,
                stateColumn,
                gmNameColumn,
                assignColumn
        );

        // 添加过滤器
        txt_tableView.getFilters().addAll(
                new IntegerFilter<>("数据ID", AqiFeedback::getAfId)
        );
    }

    public void RefreshTable() {
        ObservableList<AqiFeedback> data = FXCollections.observableArrayList();
        try (InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/aqi_feedback.json")) {
            List<AqiFeedback> afList = objectMapper.readValue(inputStream, new TypeReference<List<AqiFeedback>>() {
            });
            data.addAll(afList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        txt_tableView.setItems(data);
    }

    private void showDetailsDialog(AqiFeedback feedback) {
        String content = String.format(
                "反馈ID: %d\n反馈人: %s\n所在省份: %s\n城市: %s\n详细地址: %s\n信息描述: %s\n估计等级: %s\n反馈日期: %s\n状态: %s\n管理员: %s",
                feedback.getAfId(),
                feedback.getAfName(),
                feedback.getProviceName(),
                feedback.getCityName(),
                feedback.getAddress(),
                feedback.getInfomation(),
                feedback.getEstimateGrade(),
                feedback.getDate(),
                feedback.getState(),
                feedback.getGmName()
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
