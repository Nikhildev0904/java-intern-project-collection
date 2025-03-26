package com.cognitree.internship.report_gen.reports;

import com.cognitree.internship.report_gen.BuyRecord;
import com.cognitree.internship.report_gen.Report;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseCountReport implements Report {

    @Override
    public void generateReport(List<BuyRecord> records, String outputDir) throws IOException {
        Map<Integer, Integer> purchaseCountMap = new HashMap<>();
        addRecord(records, purchaseCountMap);
        writeReport(outputDir, purchaseCountMap);
    }

    @Override
    public String getName() {
        return "purchase_count";
    }

    private void addRecord(List<BuyRecord> records, Map<Integer, Integer> purchaseCountMap) {
        for (BuyRecord record : records) {
            int itemID = record.itemID();
            purchaseCountMap.put(itemID, purchaseCountMap.getOrDefault(itemID, 0) + 1);
        }
    }

    private void writeReport(String outputDir, Map<Integer, Integer> purchaseCountMap) throws IOException {
        Path outputPath = Paths.get(outputDir, "report_purchase_count.csv");
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputPath.toFile());
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {
            bufferedWriter.write("ItemId,PurchaseEventCount");
            bufferedWriter.newLine();
            for (Map.Entry<Integer, Integer> entry : purchaseCountMap.entrySet()) {
                bufferedWriter.write(entry.getKey() + "," + entry.getValue());
                bufferedWriter.newLine();
            }
        }
        System.out.println("Purchase Count Report Generated Successfully");
    }
}
