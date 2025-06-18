package com.nep.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.io.RWJsonTest;
import com.nep.po.Report;
import com.nep.service.ReportService;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private static final String REPORT_FILE_PATH = "NepDatas/JSONData/reports.json";
    private static final ClassLoader classLoader = RWJsonTest.class.getClassLoader();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void createReport(Report report) {
        List<Report> reports = getAllReports();
        if (reports == null) {
            reports = new ArrayList<>();
        }
        report.setReportId(reports.size() + 1);
        reports.add(report);
        try (OutputStream outputStream = new FileOutputStream(
                classLoader.getResource(REPORT_FILE_PATH).getFile())) {
            objectMapper.writeValue(outputStream, reports);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Report getReportByAfId(Integer afId) {
        List<Report> reports = getAllReports();
        if (reports != null) {
            for (Report report : reports) {
                if (report.getAfId().equals(afId)) {
                    return report;
                }
            }
        }
        return null;
    }

    @Override
    public List<Report> getAllReports() {
        try (InputStream inputStream = classLoader.getResourceAsStream(REPORT_FILE_PATH)) {
            if (inputStream == null) return new ArrayList<>();
            return objectMapper.readValue(inputStream, new TypeReference<List<Report>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Report> getReportsByCreator(String creator) {
        List<Report> allReports = getAllReports();
        return allReports.stream()
                .filter(report -> report.getCreator().equals(creator))
                .collect(Collectors.toList());
    }
}
