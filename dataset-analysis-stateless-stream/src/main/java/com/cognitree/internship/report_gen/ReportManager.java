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
        PurchaseDataParser dataParser = new PurchaseDataParser();
        this.records = dataParser.parseRawData(inputFile);
        this.outputDir = outputDir;
        loadReports();
    }

    public void generateAllReports() throws IOException {
        logger.info("Generating all reports");
        for (Report report : reports.values()) {
            report.generateReport(records, outputDir);
        }
    }

    public void generateReport(String reportName) throws IOException {
        if (!reports.containsKey(reportName)) {
            logger.info("Invalid report named '{}'", reportName);
            return;
        }
        Report report = reports.get(reportName);
        report.generateReport(records, outputDir);
    }

    private void loadReports() {
        logger.info("Loading all reports using service loader");
        ServiceLoader<Report> serviceLoader = ServiceLoader.load(Report.class);
        for (Report report : serviceLoader) {
            reports.put(report.getName(), report);
        }
    }
}
