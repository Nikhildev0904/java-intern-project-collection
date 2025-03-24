package com.cognitree.internship.report_gen;

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
            if (args.length == 2) {
                reportManager.generateAllReports();
            } else {

                int i = 2;
                while (i < args.length) {
                    String reportName = args[i];
                    reportManager.generateReport(reportName);
                    i++;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error during purchase data analysis: " + e.getMessage()); // Catches any other unknown errors
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
