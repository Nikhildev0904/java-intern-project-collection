package com.cognitree.internship.yoochoose_purchase_data_analysis;

public class PurchaseAnalyzer {

    public static void main(String[] args) {
        String inputFile = args[0];
        String outputDir = args[1];
        ReportManager reportManager = new ReportManager(inputFile, outputDir);
        reportManager.generateAllReports();
    }
}
