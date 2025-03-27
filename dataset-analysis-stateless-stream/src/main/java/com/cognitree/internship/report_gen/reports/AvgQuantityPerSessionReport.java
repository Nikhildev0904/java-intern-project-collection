package com.cognitree.internship.report_gen.reports;

import com.cognitree.internship.report_gen.BuyRecord;
import com.cognitree.internship.report_gen.Report;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class AvgQuantityPerSessionReport implements Report {

    @Override
    public void generateReport(List<BuyRecord> records, String outputDir) throws IOException {
        Map<Integer, Set<Integer>> distinctSessionMap = new HashMap<>();
        Map<Integer, Long> totalQuantityMap = new HashMap<>();
        addRecord(records, distinctSessionMap, totalQuantityMap);
        writeReport(outputDir, distinctSessionMap, totalQuantityMap);
    }

    @Override
    public String getName() {
        return "avg_quantity_session";
    }

    private void addRecord(List<BuyRecord> records, Map<Integer, Set<Integer>> distinctSessionMap,
                           Map<Integer, Long> totalQuantityMap) {
        distinctSessionMap.putAll(records.stream()
                .collect(Collectors.groupingBy(BuyRecord::itemID,
                        Collectors.mapping(BuyRecord::sessionID, Collectors.toSet())))
        );
        totalQuantityMap.putAll(
                records.stream()
                        .collect(Collectors.groupingBy(BuyRecord::itemID,
                                Collectors.summingLong(BuyRecord::quantity)))
        );
    }

    private void writeReport(String outputDir, Map<Integer, Set<Integer>> distinctSessionMap,
                             Map<Integer, Long> totalQuantityMap) throws IOException {
        Path outputPath = Paths.get(outputDir, "report_average_quantity_per_session.csv");
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputPath.toFile());
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {
            bufferedWriter.write("ItemId,AvgQuantityPerSession");
            bufferedWriter.newLine();
            for (Map.Entry<Integer, Long> entry : totalQuantityMap.entrySet()) {
                int key = entry.getKey();
                double value = (double) entry.getValue() / distinctSessionMap.get(key).size();
                bufferedWriter.write(key + "," + value);
                bufferedWriter.newLine();
            }
        }
        System.out.println("Average Quantity Per Session Report Generated Successfully");
    }
}
