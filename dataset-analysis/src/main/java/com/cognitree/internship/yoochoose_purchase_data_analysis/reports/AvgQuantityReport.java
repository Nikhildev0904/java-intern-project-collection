package com.cognitree.internship.yoochoose_purchase_data_analysis.reports;

import com.cognitree.internship.yoochoose_purchase_data_analysis.PurchaseDataParser;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Set;

public class AvgQuantityReport implements Report {
    private Map<Integer, Set<Integer>> distinctSessionMap;
    private Map<Integer, Integer> totalQuantityMap;

    public AvgQuantityReport() {
    }

    @Override
    public void generateReport(String outputDir) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputDir + "/report_average_quantity.csv");
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {
            Map<Integer, Integer> totalQuantityMap = this.totalQuantityMap;
            Map<Integer, Set<Integer>> distinctSessionMap = this.distinctSessionMap;
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

    @Override
    public void init(PurchaseDataParser dataParser) {
        this.totalQuantityMap = dataParser.getTotalQuantityMap();
        this.distinctSessionMap = dataParser.getDistinctSessionMap();
    }
}
