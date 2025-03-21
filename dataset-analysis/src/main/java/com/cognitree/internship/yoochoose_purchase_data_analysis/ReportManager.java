package com.cognitree.internship.yoochoose_purchase_data_analysis;

import com.cognitree.internship.yoochoose_purchase_data_analysis.reports.Report;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class ReportManager {
    private final List<Report> reportList;
    private final PurchaseDataParser dataParser;
    private final String outputDir;

    public ReportManager(String inputFile, String outputDir) {
        this.dataParser = new PurchaseDataParser(inputFile);
        this.outputDir = outputDir;
        reportList = new ArrayList<>();
        loadReports();
    }

    public void generateAllReports() {
        for (Report report : reportList) {
            report.generateReport(outputDir);
        }
    }

    private void loadReports() {
        ServiceLoader<Report> serviceLoader = ServiceLoader.load(Report.class);
        for (Report report : serviceLoader) {
            report.init(dataParser);
            reportList.add(report);
        }
    }
}
