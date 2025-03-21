package com.cognitree.internship.yoochoose_purchase_data_analysis.reports;

import com.cognitree.internship.yoochoose_purchase_data_analysis.PurchaseDataParser;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Set;

public class DistinctSessionReport implements Report {
    private Map<Integer, Set<Integer>> distinctSessionMap;

    public DistinctSessionReport() {
    }

    @Override
    public void generateReport(String outputDir) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputDir + "/report_distinct_sessions_count.csv");
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {
            Map<Integer, Set<Integer>> distinctSessionMap = this.distinctSessionMap;
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

    @Override
    public void init(PurchaseDataParser dataParser) {
        this.distinctSessionMap = dataParser.getDistinctSessionMap();
    }
}
