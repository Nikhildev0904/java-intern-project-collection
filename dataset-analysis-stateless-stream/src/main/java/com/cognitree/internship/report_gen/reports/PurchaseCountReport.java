package com.cognitree.internship.report_gen.reports;

import com.cognitree.internship.report_gen.BuyRecord;
import com.cognitree.internship.report_gen.Report;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PurchaseCountReport implements Report {

    @Override
    public void generateReport(List<BuyRecord> records, String outputDir) throws IOException {
        Map<Integer, Long> purchaseCountMap = addRecord(records);
        writeReport(outputDir, purchaseCountMap);
    }

    @Override
    public String getName() {
        return "purchase_count";
    }

    private Map<Integer, Long> addRecord(List<BuyRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(BuyRecord::itemID, Collectors.counting()));
    }

    private void writeReport(String outputDir, Map<Integer, Long> purchaseCountMap) throws IOException {
        Path outputPath = Paths.get(outputDir, "report_purchase_count.csv");
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputPath.toFile());
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {
            bufferedWriter.write("ItemId,PurchaseEventCount");
            bufferedWriter.newLine();
            for (Map.Entry<Integer, Long> entry : purchaseCountMap.entrySet()) {
                bufferedWriter.write(entry.getKey() + "," + entry.getValue());
                bufferedWriter.newLine();
            }
        }
        System.out.println("Purchase Count Report Generated Successfully");
    }
}
