package com.cognitree.internship.report_gen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ServiceLoader;

public class PurchaseAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseAnalyzer.class);

    public static void main(String[] args) {
        logger.info("Application started with {}", (Object) args);
        if (args.length < 1) {
            logger.warn("No arguments provided");
            printUsage();
            return;
        }
        String command = args[0];
        if ("listreports".equalsIgnoreCase(command)) {
            listAvailableReports();
            return;
        }
        if (!"generate".equalsIgnoreCase(command) || args.length < 4) {
            logger.error("Invalid command or insufficient arguments: {}", (Object) args);
            printUsage();
            return;
        }
        String inputFile = args[args.length - 2];
        String outputDir = args[args.length - 1];
        try {
            validateInputFile(inputFile);
            validateOutputDir(outputDir);
            logger.info("Input file and output directory validated");
            ReportManager reportManager = new ReportManager(inputFile, outputDir);
            if ("all".equalsIgnoreCase(args[1])) {
                if (args.length > 4) {
                    logger.error("Invalid usage with 'all' command, Too many arguments.");
                    printUsage();
                    return;
                }
                reportManager.generateAllReports();
                logger.info("All reports generated successfully.");
            } else {
                for (int i = 1; i < args.length - 2; i++) {
                    reportManager.generateReport(args[i]);
                }
                logger.info("Selected reports generated successfully.");
            }
        } catch (IllegalArgumentException e) {
            logger.error("Validation failed: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred: ", e);
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
