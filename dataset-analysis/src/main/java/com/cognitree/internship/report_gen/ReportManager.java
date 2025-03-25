package com.cognitree.internship.report_gen;

import com.cognitree.internship.report_gen.reports.Report;

import java.io.IOException;
import java.util.*;

public class ReportManager {
    private final Map<String, Report> reports = new HashMap<>();
    private final List<BuyRecord> records;
    private final String outputDir;

    public ReportManager(String inputFile, String outputDir) throws IOException {
        PurchaseDataParser dataParser = new PurchaseDataParser(inputFile);
        this.records = dataParser.getRecords();
        this.outputDir = outputDir;
        loadReports();
    }

    public void generateAllReports() throws IOException {
        initializeReport("");
        for (Report report : reports.values()) {
            report.generateReport(outputDir);
        }
    }

    public void generateReport(String reportName) throws IOException {
        initializeReport(reportName);
        Report report = reports.get(reportName);
        report.generateReport(outputDir);
    }

    private void loadReports() {
        ServiceLoader<Report> serviceLoader = ServiceLoader.load(Report.class);
        for (Report report : serviceLoader) {
            reports.put(report.getName(), report);
        }
    }

    private void initializeReport(String reportName) {
        Report report = reports.get(reportName);
        if (reportName.isEmpty()) {
            for (Report eachReport : reports.values()) {
                for (BuyRecord record : records) {
                    eachReport.addRecord(record);
                }
            }
        } else {
            try {
                for (BuyRecord record : records) {
                    report.addRecord(record);
                }
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("Invalid report name '" + reportName + "' or report not available");
            }
        }
    }
}
