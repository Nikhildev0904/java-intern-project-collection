package com.cognitree.internship.report_gen.reports;

import com.cognitree.internship.report_gen.BuyRecord;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class PurchaseCountReport implements Report {
    private final Map<Integer, Integer> purchaseCountMap = new HashMap<>();

    @Override
    public void generateReport(String outputDir) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputDir + "/report_purchase_count.csv");
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {
            Map<Integer, Integer> purchaseCountMap = this.purchaseCountMap;
            bufferedWriter.write("ItemId,PurchaseEventCount");
            bufferedWriter.newLine();
            for (Map.Entry<Integer, Integer> entry : purchaseCountMap.entrySet()) {
                bufferedWriter.write(entry.getKey() + "," + entry.getValue());
                bufferedWriter.newLine();
            }
            System.out.println("Report Generated Successfully");
        }
    }

    @Override
    public void addRecord(BuyRecord record) {
        int itemID = record.itemID();
        purchaseCountMap.put(itemID, purchaseCountMap.getOrDefault(itemID, 0) + 1);
    }

    @Override
    public String getName() {
        return "PurchaseCountReport";
    }
}
