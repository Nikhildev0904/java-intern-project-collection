package com.cognitree.internship.data_analysis;

import java.io.*;
import java.util.Map;
import java.util.Set;

public class ReportManager {
    private final DataParser dataParser;
    private final String outputDir;

    public ReportManager(String pathToDataSet, String outputDir) {
        this.dataParser = new DataParser(pathToDataSet);
        this.outputDir = outputDir;
    }

    public enum ReportType {
        PURCHASE_COUNT, DISTINCT_SESSIONS, AVERAGE_QUANTITY_PER_ITEM
    }

    public void generateAllReports() {
        generatePurchaseEventReport();
        generateDistinctSessionReport();
        generateAvgQuantityReport();
    }

    public void generateReport(ReportType reportType) {
        if (reportType == ReportType.PURCHASE_COUNT) {
            generatePurchaseEventReport();
        } else if (reportType == ReportType.DISTINCT_SESSIONS) {
            generateDistinctSessionReport();
        } else if (reportType == ReportType.AVERAGE_QUANTITY_PER_ITEM) {
            generateAvgQuantityReport();
        }
    }

    private void generateAvgQuantityReport() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputDir + "/report_average_quantity.csv");
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {
            Map<Integer, Integer> totalQuantityMap = this.dataParser.getTotalQuantityMap();
            Map<Integer, Set<Integer>> distinctSessionMap = this.dataParser.getDistinctSessionMap();
            bufferedWriter.write("ItemId,AvgQuantityPerSession");
            bufferedWriter.newLine();
            for (int key : totalQuantityMap.keySet()) {
                double value = (double) (totalQuantityMap.get(key)) / (distinctSessionMap.get(key).size());
                String line = key + "," + value;
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            System.out.println("Report Generated Successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateDistinctSessionReport() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputDir + "/report_distinct_sessions_count.csv");
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {
            Map<Integer, Set<Integer>> distinctSessionMap = this.dataParser.getDistinctSessionMap();
            bufferedWriter.write("ItemId,DistinctSessionCount");
            bufferedWriter.newLine();
            for (int key : distinctSessionMap.keySet()) {
                String line = key + "," + distinctSessionMap.get(key).size();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            System.out.println("Report Generated Successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generatePurchaseEventReport() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputDir + "/report_purchase_count.csv");
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {
            Map<Integer, Integer> purchaseCountMap = this.dataParser.getPurchaseCountMap();
            bufferedWriter.write("ItemId,PurchaseEventCount");
            bufferedWriter.newLine();
            for (int key : purchaseCountMap.keySet()) {
                String line = key + "," + purchaseCountMap.get(key);
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            System.out.println("Report Generated Successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
