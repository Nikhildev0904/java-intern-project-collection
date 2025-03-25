package com.cognitree.internship.report_gen;

import com.cognitree.internship.report_gen.reports.Report;

import java.io.File;
import java.util.ServiceLoader;

public class PurchaseAnalyzer {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Use: java PurchaseAnalyzer <Input File> <Output Directory> <Command> [ReportName...]");
            System.out.println("Commands:");
            System.out.println("  listreports - Lists all available reports");
            System.out.println("  generate all - Generates all available reports");
            System.out.println("  generate <ReportName...> - Generates the specified reports)");
            return;
        }
        String inputFile = args[0];
        String outputDir = args[1];
        String command = args[2];
        try {
            validateInputFile(inputFile);
            validateOutputDir(outputDir);
            ReportManager reportManager = new ReportManager(inputFile, outputDir);
            if ("listreports".equalsIgnoreCase(command)) {
                listAvailableReports();
            } else if ("generate".equalsIgnoreCase(command)) {
                if (args.length == 4 && "all".equalsIgnoreCase(args[3])) {
                    reportManager.generateAllReports();
                } else if (args.length >= 4) {
                    for (int i = 3; i < args.length; i++) {
                        reportManager.generateReport(args[i]);
                    }
                } else {
                    throw new IllegalArgumentException("Missing report name, Use 'generate all' or 'generate <ReportName...>'");
                }
            } else {
                throw new IllegalArgumentException("Invalid command: " + command + ". Use 'listReports' or 'generate' to know more");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error during purchase data analysis: " + e.getMessage());
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

    private static void listAvailableReports() {
        ServiceLoader<Report> allReports = ServiceLoader.load(Report.class);
        System.out.println("\nAll available reports:");
        for (Report report : allReports) {
            System.out.println("- " + report.getName());
        }
    }
}
