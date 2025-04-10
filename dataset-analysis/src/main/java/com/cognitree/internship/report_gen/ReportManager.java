package com.cognitree.internship.report_gen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class ReportManager {

    private static final Logger logger = LoggerFactory.getLogger(ReportManager.class);

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
        logger.info("Generating all reports");
        initializeReport("");
        for (Report report : reports.values()) {
            report.generateReport(outputDir);
        }
    }

    public void generateReport(String reportName) throws IOException {
        if (!reports.containsKey(reportName)) {
            logger.warn("Invalid report named '{}'", reportName);
            return;
        }
        initializeReport(reportName);
        Report report = reports.get(reportName);
        report.generateReport(outputDir);
    }

    private void loadReports() {
        logger.info("Loading all reports using service loader");
        ServiceLoader<Report> serviceLoader = ServiceLoader.load(Report.class);
        for (Report report : serviceLoader) {
            reports.put(report.getName(), report);
        }
    }

    private void initializeReport(String reportName) {
        logger.info("initializing report(s) with data");
        Report report = reports.get(reportName);
        if (reportName.isEmpty()) {
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
