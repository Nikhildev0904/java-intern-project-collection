package com.cognitree.internship.analytics.purchase;

import java.io.File;

public class PurchaseAnalyzer {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Use Command line arguments: PurchaseAnalyzer <Input File> <Output Directory> [ReportName]");
            return;
        }
        String inputFile = args[0];
        String outputDir = args[1];
        try {
            validateInputFile(inputFile);
            validateOutputDir(outputDir);
            ReportManager reportManager = new ReportManager(inputFile, outputDir);
            reportManager.generateAllReports();
            if (args.length == 3) {
                String reportName = args[2];
                reportManager.generateReport(reportName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void validateInputFile(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            throw new IllegalArgumentException("Invalid input file: " + path);
        }
    }

    private static void validateOutputDir(String path) {
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory() || !dir.canWrite()) {
            throw new IllegalArgumentException("Invalid output directory: " + path);
        }
    }
}
