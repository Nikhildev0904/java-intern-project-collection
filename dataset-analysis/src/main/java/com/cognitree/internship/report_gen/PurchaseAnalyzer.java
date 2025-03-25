package com.cognitree.internship.report_gen;

import com.cognitree.internship.report_gen.reports.Report;

import java.io.File;
import java.util.ServiceLoader;

public class PurchaseAnalyzer {

    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
            return;
        }
        String command = args[0];
        if ("listreports".equalsIgnoreCase(command)) {
            listAvailableReports();
            return;
        }
        if (!"generate".equalsIgnoreCase(command) || args.length < 4) {
            System.out.println("Error: Invalid command or missing arguments.");
            printUsage();
            return;
        }
        String inputFile = args[args.length - 2];
        String outputDir = args[args.length - 1];
        try {
            validateInputFile(inputFile);
            validateOutputDir(outputDir);
            ReportManager reportManager = new ReportManager(inputFile, outputDir);
            if ("all".equalsIgnoreCase(args[1])) {
                if (args.length > 4) {
                    System.out.println("Error: Invalid usage");
                    printUsage();
                    return;
                }
                reportManager.generateAllReports();
                System.out.println("All reports generated successfully.");
            } else {
                for (int i = 1; i < args.length - 2; i++) {
                    reportManager.generateReport(args[i]);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void printUsage() {
        System.out.println("Use:");
        System.out.println("  To list reports:");
        System.out.println("    java PurchaseAnalyzer listreports");
        System.out.println("  To generate reports:");
        System.out.println("    java PurchaseAnalyzer generate <ReportName...> <Input File> <Output Directory>");
        System.out.println("    (Use 'all' instead of <ReportName...> to generate all reports)");
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

    private static void listAvailableReports() {
        ServiceLoader<Report> allReports = ServiceLoader.load(Report.class);
        System.out.println("\nAll available reports:");
        for (Report report : allReports) {
            System.out.println("- " + report.getName());
        }
    }
}
