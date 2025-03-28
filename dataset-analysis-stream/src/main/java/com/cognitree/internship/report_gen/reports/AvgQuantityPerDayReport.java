package com.cognitree.internship.report_gen.reports;

import com.cognitree.internship.report_gen.BuyRecord;
import com.cognitree.internship.report_gen.Report;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.util.HashMap;
import java.util.Map;

public class AvgQuantityPerDayReport implements Report {
    private final Map<DayOfWeek, Map<Integer, Pair>> quantityPerDayMap = new HashMap<>();

    @Override
    public void generateReport(String outputDir) throws IOException {
        Path outputPath = Paths.get(outputDir, "report_avg_quantity_per_day.csv");
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outputPath.toFile())))) {
            writer.write("DayOfWeek,ItemID,AvgQuantity");
            writer.newLine();
            for (Map.Entry<DayOfWeek, Map<Integer, Pair>> dayEntry : quantityPerDayMap.entrySet()) {
                DayOfWeek day = dayEntry.getKey();
                for (Map.Entry<Integer, Pair> itemEntry : dayEntry.getValue().entrySet()) {
                    int itemId = itemEntry.getKey();
                    int totalQuantity = itemEntry.getValue().totalQuantity;
                    int noOfInstances = itemEntry.getValue().noOfInstances;
                    double avgQuantity = (double) totalQuantity / noOfInstances;
                    writer.write(day + "," + itemId + "," + avgQuantity);
                    writer.newLine();
                }
            }
            System.out.println("Avg Quantity Per Day of Week Report Generated Successfully");
        }
    }

    @Override
    public void addRecord(BuyRecord record) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.parse(record.timeStamp()), ZoneOffset.UTC);
        DayOfWeek day = dateTime.getDayOfWeek();
        int itemID = record.itemID();
        int quantity = record.quantity();
        if (!quantityPerDayMap.containsKey(day)) {
            quantityPerDayMap.put(day, new HashMap<>());
        }
        if (!quantityPerDayMap.get(day).containsKey(itemID)) {
            quantityPerDayMap.get(day).put(itemID, new Pair(0, 0));
        }
        quantityPerDayMap.get(day).get(itemID).totalQuantity += quantity;
        quantityPerDayMap.get(day).get(itemID).noOfInstances++;
    }

    @Override
    public String getName() {
        return "avg_quantity_day";
    }

    private static class Pair {
        private int totalQuantity;
        private int noOfInstances;

        private Pair(int noOfInstances, int totalQuantity) {
            this.noOfInstances = noOfInstances;
            this.totalQuantity = totalQuantity;
        }
    }
}
