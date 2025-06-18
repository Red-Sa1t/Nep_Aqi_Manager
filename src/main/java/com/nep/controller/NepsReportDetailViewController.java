package com.nep.controller;

import com.nep.manager.TipsManager;
import com.nep.po.Report;
import com.nep.po.Supervisor;
import com.nep.service.ReportService;
import com.nep.service.impl.ReportServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NepsReportDetailViewController implements Initializable {
    @FXML
    private TextField afIdField;
    @FXML
    private TextArea pollutionCauseArea;
    @FXML
    private TextArea shortTermSolutionArea;
    @FXML
    private TextArea longTermSolutionArea;

    private ReportService reportService = new ReportServiceImpl();
    public static Report report;
    public static Supervisor supervisor;
    public static Stage primaryStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (report != null) {
            loadReportData();
        }
    }

    private void loadReportData() {
        afIdField.setText(report.getAfId().toString());
        pollutionCauseArea.setText(report.getPollutionCause());
        shortTermSolutionArea.setText(report.getShortTermSolution());
        longTermSolutionArea.setText(report.getLongTermSolution());
    }

    @FXML
    public void saveReport() {
        try {
            Integer afId = Integer.parseInt(afIdField.getText());
            String pollutionCause = pollutionCauseArea.getText();
            String shortTermSolution = shortTermSolutionArea.getText();
            String longTermSolution = longTermSolutionArea.getText();

            if (pollutionCause.isEmpty() || shortTermSolution.isEmpty()) {
                TipsManager.getInstance().showError("污染原因和近期解决方案为空");
                return;
            }

            Report newReport;
            if (report == null) {
                newReport = new Report(afId, pollutionCause, shortTermSolution,
                        longTermSolution, supervisor.getRealName());
            } else {
                newReport = report;
                newReport.setPollutionCause(pollutionCause);
                newReport.setShortTermSolution(shortTermSolution);
                newReport.setLongTermSolution(longTermSolution);
            }

            reportService.createReport(newReport);
            TipsManager.getInstance().showInfo("报告保存成功");

        } catch (NumberFormatException e) {
            TipsManager.getInstance().showError("请输入有效的反馈编号");
        }
    }
}