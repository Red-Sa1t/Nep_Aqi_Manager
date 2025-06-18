package com.nep.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.io.RWJsonTest;
import com.nep.po.Report;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class NepReportViewController implements Initializable {

    private static final ClassLoader classLoader = RWJsonTest.class.getClassLoader();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String REPORT_JSON_PATH = "NepDatas/JSONData/reports.json";
    private final Stage stage;
    public StackPane rootStackPane;
    public MFXButton btnShowDetails;
    MFXGenericDialog dialogContent;
    MFXStageDialog dialog;
    @FXML
    private MFXTableView<Report> reportTable;

    NepReportViewController(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        btnShowDetails.setOnAction(event -> {
            Report selected = reportTable.getSelectionModel().getSelectedValue();
            if (selected != null) {
                showDetailsDialog(selected);
            } else {
                Notifications.create()
                        .title("提示")
                        .text("请先选择一条报告记录")
                        .graphic(null)
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showWarning();
            }
        });
    }

    private void setupTable() {
        // 日期格式化器
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 定义列
        MFXTableColumn<Report> reportIdCol = new MFXTableColumn<>("报告ID", true, Comparator.comparing(Report::getReportId));
        MFXTableColumn<Report> afIdCol = new MFXTableColumn<>("反馈ID", Comparator.comparing(Report::getAfId));
        MFXTableColumn<Report> causeCol = new MFXTableColumn<>("污染原因", Comparator.comparing(Report::getPollutionCause));
        MFXTableColumn<Report> shortCol = new MFXTableColumn<>("短期解决方案", Comparator.comparing(Report::getShortTermSolution));
        MFXTableColumn<Report> longCol = new MFXTableColumn<>("长期解决方案", Comparator.comparing(Report::getLongTermSolution));
        MFXTableColumn<Report> dateCol = new MFXTableColumn<>("创建时间", Comparator.comparing(Report::getCreateDate));
        MFXTableColumn<Report> creatorCol = new MFXTableColumn<>("创建人", Comparator.comparing(Report::getCreator));

        // 设置每列显示内容
        reportIdCol.setRowCellFactory(item -> new MFXTableRowCell<>(Report::getReportId));
        afIdCol.setRowCellFactory(item -> new MFXTableRowCell<>(Report::getAfId));
        causeCol.setRowCellFactory(item -> new MFXTableRowCell<>(Report::getPollutionCause));
        shortCol.setRowCellFactory(item -> new MFXTableRowCell<>(Report::getShortTermSolution));
        longCol.setRowCellFactory(item -> new MFXTableRowCell<>(Report::getLongTermSolution));
        dateCol.setRowCellFactory(item -> new MFXTableRowCell<>(report -> sdf.format(report.getCreateDate())));
        creatorCol.setRowCellFactory(item -> new MFXTableRowCell<>(Report::getCreator));

        // 设置居中对齐
        causeCol.setAlignment(Pos.CENTER);
        shortCol.setAlignment(Pos.CENTER);
        longCol.setAlignment(Pos.CENTER);
        dateCol.setAlignment(Pos.CENTER);
        creatorCol.setAlignment(Pos.CENTER);

        // 添加列
        reportTable.getTableColumns().addAll(
                reportIdCol, afIdCol, causeCol, shortCol, longCol, dateCol, creatorCol
        );

        // 添加过滤器
        reportTable.getFilters().addAll(
                new IntegerFilter<>("报告ID", Report::getReportId)
        );

        // 加载数据
        ObservableList<Report> data = FXCollections.observableArrayList();
        try (InputStream inputStream = classLoader.getResourceAsStream(REPORT_JSON_PATH)) {
            List<Report> reports = objectMapper.readValue(inputStream, new TypeReference<List<Report>>() {
            });
            data.addAll(reports);
        } catch (Exception e) {
            e.printStackTrace();
        }

        reportTable.setItems(data);
    }

    private void showDetailsDialog(Report report) {
        String content = String.format(
                "报告ID: %d\n反馈ID: %d\n污染原因: %s\n近期方案: %s\n长期方案: %s\n创建时间: %s\n创建人: %s",
                report.getReportId(),
                report.getAfId(),
                report.getPollutionCause(),
                report.getShortTermSolution(),
                report.getLongTermSolution(),
                report.getCreateDate().toString(),
                report.getCreator()
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
