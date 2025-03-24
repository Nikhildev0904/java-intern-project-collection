package com.cognitree.internship.analytics.purchase;

import com.cognitree.internship.analytics.purchase.reports.Report;

import java.util.*;

public class ReportManager {
    private final Map<String, Report> reports = new HashMap<>();
    private final List<BuyRecord> records;
    private final String outputDir;

    public ReportManager(String inputFile, String outputDir) {
        PurchaseDataParser dataParser = new PurchaseDataParser(inputFile);
        this.records = dataParser.getRecords();
        this.outputDir = outputDir;
        loadReports();
    }

    public void generateAllReports() {
        initializeReport(null);
        for (Report report : reports.values()) {
            report.generateReport(outputDir);
        }
    }

    public void generateReport(String reportName) {
        Report report = reports.get(reportName);
        initializeReport(report);
        report.generateReport(outputDir);
    }

    private void loadReports() {
        ServiceLoader<Report> serviceLoader = ServiceLoader.load(Report.class);
        for (Report report : serviceLoader) {
            reports.put(report.getName(), report);
        }
    }

    private void initializeReport(Report report) {
        if (report == null) {
            for (Report eachReport : reports.values()) {
                for (BuyRecord record : records) {
                    eachReport.addRecord(record);
                }
            }
        } else {
            for (BuyRecord record : records) {
                report.addRecord(record);
            }
        }
    }
}
