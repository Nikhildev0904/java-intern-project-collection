package com.cognitree.internship.report_gen.reports;

import com.cognitree.internship.report_gen.BuyRecord;
import com.cognitree.internship.report_gen.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DistinctSessionReport implements Report {

    private static final Logger logger = LoggerFactory.getLogger(DistinctSessionReport.class);

    @Override
    public void generateReport(List<BuyRecord> records, String outputDir) throws IOException {
        Map<Integer, Set<Integer>> distinctSessionMap = new HashMap<>();
        addRecord(records, distinctSessionMap);
        writeReport(outputDir, distinctSessionMap);
    }

    @Override
    public String getName() {
        return "distinct_sessions";
    }

    private void addRecord(List<BuyRecord> records, Map<Integer, Set<Integer>> distinctSessionMap) {
        for (BuyRecord record : records) {
            int itemID = record.itemID();
            int sessionID = record.sessionID();
            if (!distinctSessionMap.containsKey(itemID)) {
                distinctSessionMap.put(itemID, new HashSet<>());
            }
            distinctSessionMap.get(itemID).add(sessionID);
        }
    }

    private void writeReport(String outputDir, Map<Integer, Set<Integer>> distinctSessionMap) throws IOException {
        Path outputPath = Paths.get(outputDir, "report_distinct_sessions_count.csv");
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputPath.toFile());
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {
            bufferedWriter.write("ItemId,DistinctSessionCount");
            bufferedWriter.newLine();
            for (Map.Entry<Integer, Set<Integer>> entry : distinctSessionMap.entrySet()) {
                bufferedWriter.write(entry.getKey() + "," + entry.getValue().size());
                bufferedWriter.newLine();
            }
        }
        logger.info("Distinct Sessions Report Generated Successfully");
    }
}
