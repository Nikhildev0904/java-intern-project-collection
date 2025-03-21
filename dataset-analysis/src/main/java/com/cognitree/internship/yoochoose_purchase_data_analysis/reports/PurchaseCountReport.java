package com.cognitree.internship.yoochoose_purchase_data_analysis.reports;

import com.cognitree.internship.yoochoose_purchase_data_analysis.PurchaseDataParser;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

public class PurchaseCountReport implements Report {
    private Map<Integer, Integer> purchaseCountMap;

    public PurchaseCountReport() {
    }

    @Override
    public void generateReport(String outputDir) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputDir + "/report_purchase_count.csv");
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {
            Map<Integer, Integer> purchaseCountMap = this.purchaseCountMap;
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

    @Override
    public void init(PurchaseDataParser dataParser) {
        this.purchaseCountMap = dataParser.getPurchaseCountMap();
    }
}
