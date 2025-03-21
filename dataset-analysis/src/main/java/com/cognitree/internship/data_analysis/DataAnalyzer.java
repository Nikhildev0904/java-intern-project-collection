package com.cognitree.internship.data_analysis;

public class DataAnalyzer {

    public static void main(String[] args) {
        String pathToDataSet = "D:/practise/practice/Data_analysis/archive/yoochoose-data/yoochoose-buys.dat";
        String outputDir = "D:/practise/practice/Data_analysis/archive/yoochoose-data/output";
        ReportManager reportManager = new ReportManager(pathToDataSet, outputDir);
        reportManager.generateAllReports();
        reportManager.generateReport(ReportManager.ReportType.PURCHASE_COUNT); // if the user needs a specific type of report
    }
}
