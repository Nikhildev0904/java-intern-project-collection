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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AvgQuantityPerSessionReport implements Report {

    private static final Logger logger = LoggerFactory.getLogger(AvgQuantityPerSessionReport.class);

    private final Map<Integer, Set<Integer>> distinctSessionMap = new HashMap<>();
    private final Map<Integer, Integer> totalQuantityMap = new HashMap<>();

    @Override
    public void generateReport(String outputDir) throws IOException {
        Path outputPath = Paths.get(outputDir, "report_average_quantity_per_session.csv");
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputPath.toFile());
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
            logger.info("Average Quantity Per Session Report Generated Successfully");
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
        return "avg_quantity_session";
    }
}
