package com.nep.po;

import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer reportId;
    private Integer afId; // 关联的反馈ID
    private String pollutionCause; // 污染原因分析
    private String shortTermSolution; // 近期解决方案
    private String longTermSolution; // 长期解决方案
    private Date createDate; // 报告创建日期
    private String creator; // 报告创建人

    // 构造函数、getter和setter方法
    public Report() {
    }

    public Report(Integer afId, String pollutionCause, String shortTermSolution,
                  String longTermSolution, String creator) {
        this.afId = afId;
        this.pollutionCause = pollutionCause;
        this.shortTermSolution = shortTermSolution;
        this.longTermSolution = longTermSolution;
        this.createDate = new Date();
        this.creator = creator;
    }

    // Getters and Setters
    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getAfId() {
        return afId;
    }

    public void setAfId(Integer afId) {
        this.afId = afId;
    }

    public String getPollutionCause() {
        return pollutionCause;
    }

    public void setPollutionCause(String pollutionCause) {
        this.pollutionCause = pollutionCause;
    }

    public String getShortTermSolution() {
        return shortTermSolution;
    }

    public void setShortTermSolution(String shortTermSolution) {
        this.shortTermSolution = shortTermSolution;
    }

    public String getLongTermSolution() {
        return longTermSolution;
    }

    public void setLongTermSolution(String longTermSolution) {
        this.longTermSolution = longTermSolution;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", afId=" + afId +
                ", pollutionCause='" + pollutionCause + '\'' +
                ", shortTermSolution='" + shortTermSolution + '\'' +
                ", longTermSolution='" + longTermSolution + '\'' +
                ", createDate=" + createDate +
                ", creator='" + creator + '\'' +
                '}';
    }
}