package com.nep.service;

import com.nep.po.Report;

import java.util.List;

public interface ReportService {


    void createReport(Report report);

    Report getReportByAfId(Integer afId);

    List<Report> getAllReports();

    List<Report> getReportsByCreator(String creator);
}