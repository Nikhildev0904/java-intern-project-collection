package com.cognitree.internship.report_gen.reports;

import com.cognitree.internship.report_gen.BuyRecord;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DistinctSessionReport implements Report {
    private final Map<Integer, Set<Integer>> distinctSessionMap = new HashMap<>();

    @Override
    public void generateReport(String outputDir) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputDir + "/report_distinct_sessions_count.csv");
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {
            Map<Integer, Set<Integer>> distinctSessionMap = this.distinctSessionMap;
            bufferedWriter.write("ItemId,DistinctSessionCount");
            bufferedWriter.newLine();
            for (Map.Entry<Integer, Set<Integer>> entry : distinctSessionMap.entrySet()) {
                bufferedWriter.write(entry.getKey() + "," + entry.getValue().size());
                bufferedWriter.newLine();
            }
            System.out.println("Report Generated Successfully");
        }
    }

    @Override
    public void addRecord(BuyRecord record) {
        int itemID = record.itemID();
        int sessionID = record.sessionID();
        if (!distinctSessionMap.containsKey(itemID)) {
            distinctSessionMap.put(itemID, new HashSet<>());
        }
        distinctSessionMap.get(itemID).add(sessionID);
    }

    @Override
    public String getName() {
        return "DistinctSessionReport";
    }
}
