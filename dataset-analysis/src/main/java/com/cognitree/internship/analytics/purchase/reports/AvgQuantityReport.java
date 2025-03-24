package com.cognitree.internship.analytics.purchase.reports;

import com.cognitree.internship.analytics.purchase.BuyRecord;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AvgQuantityReport implements Report {
    private final Map<Integer, Set<Integer>> distinctSessionMap;
    private final Map<Integer, Integer> totalQuantityMap;

    public AvgQuantityReport() {
        distinctSessionMap = new HashMap<>();
        totalQuantityMap = new HashMap<>();
    }

    @Override
    public void generateReport(String outputDir) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputDir + "/report_average_quantity.csv");
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {
            Map<Integer, Integer> totalQuantityMap = this.totalQuantityMap;
            Map<Integer, Set<Integer>> distinctSessionMap = this.distinctSessionMap;
            bufferedWriter.write("ItemId,AvgQuantityPerSession");
            bufferedWriter.newLine();
            for (Map.Entry<Integer, Integer> entry : totalQuantityMap.entrySet()) {
                int key = entry.getKey();
                double value = (double) entry.getValue() / distinctSessionMap.get(key).size();
                bufferedWriter.write(key + "," + value);
                bufferedWriter.newLine();
            }
            System.out.println("Report Generated Successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addRecord(BuyRecord record) {
        int itemID = record.itemID();
        int sessionID = record.sessionID();
        int quantity = record.quantity();
        if (!distinctSessionMap.containsKey(itemID)) {
            distinctSessionMap.put(itemID, new HashSet<>());
        }
        distinctSessionMap.get(itemID).add(sessionID);
        totalQuantityMap.put(itemID, totalQuantityMap.getOrDefault(itemID, 0) + quantity);
    }

    @Override
    public String getName() {
        return "AverageQuantityReport";
    }
}
