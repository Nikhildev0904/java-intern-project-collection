package com.cognitree.internship.report_gen;

import java.io.IOException;
import java.util.*;

public class ReportManager {
    private final Map<String, Report> reports = new HashMap<>();
    private final String inputFile;
    private final String outputDir;

    public ReportManager(String inputFile, String outputDir) {
        this.inputFile = inputFile;
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
        if (!reports.containsKey(reportName)) {
            System.out.println("Invalid report named '" + reportName + "'");
            return;
        }
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

    private void initializeReport(String reportName) throws IOException {
        PurchaseDataParser dataParser = new PurchaseDataParser();
        if (reportName.isEmpty()) {
            dataParser.parseRawData(inputFile, record -> {
                for (Report eachReport : reports.values()) {
                    eachReport.addRecord(record);
                }
            });
        } else {
            Report report = reports.get(reportName);
            dataParser.parseRawData(inputFile, record -> report.addRecord(record));
        }
    }
}
