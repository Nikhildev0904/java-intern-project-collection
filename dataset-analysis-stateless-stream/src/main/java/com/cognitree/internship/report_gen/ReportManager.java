package com.cognitree.internship.report_gen;

import java.io.IOException;
import java.util.*;

public class ReportManager {
    private final Map<String, Report> reports = new HashMap<>();
    private final List<BuyRecord> records;
    private final String outputDir;

    public ReportManager(String inputFile, String outputDir) throws IOException {
        PurchaseDataParser dataParser = new PurchaseDataParser();
        this.records = dataParser.parseRawData(inputFile);
        this.outputDir = outputDir;
        loadReports();
    }

    public void generateAllReports() throws IOException {
        for (Report report : reports.values()) {
            report.generateReport(records, outputDir);
        }
    }

    public void generateReport(String reportName) throws IOException {
        if (!reports.containsKey(reportName)) {
            System.out.println("Invalid report named '" + reportName + "'");
            return;
        }
        Report report = reports.get(reportName);
        report.generateReport(records, outputDir);
    }

    private void loadReports() {
        ServiceLoader<Report> serviceLoader = ServiceLoader.load(Report.class);
        for (Report report : serviceLoader) {
            reports.put(report.getName(), report);
        }
    }
}
