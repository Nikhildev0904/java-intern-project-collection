package com.cognitree.internship.report_gen.reports;

import com.cognitree.internship.report_gen.BuyRecord;
import com.cognitree.internship.report_gen.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AvgQuantityPerDayReport implements Report {

    private static final Logger logger = LoggerFactory.getLogger(AvgQuantityPerDayReport.class);

    @Override
    public void generateReport(List<BuyRecord> records, String outputDir) throws IOException {
        Map<DayOfWeek, Map<Integer, Double>> quantityPerDayMap = aggregateRecords(records);
        writeReport(outputDir, quantityPerDayMap);
    }

    @Override
    public String getName() {
        return "avg_quantity_day";
    }

    private Map<DayOfWeek, Map<Integer, Double>> aggregateRecords(List<BuyRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(record -> {
                                    LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.parse(record.timeStamp()), ZoneOffset.UTC);
                                    return dateTime.getDayOfWeek();
                                },
                                Collectors.groupingBy(BuyRecord::itemID,
                                        Collectors.averagingDouble(BuyRecord::quantity))
                        )
                );
    }

    private void writeReport(String outputDir, Map<DayOfWeek, Map<Integer, Double>> quantityPerDayMap) throws IOException {
        Path outputPath = Paths.get(outputDir, "report_avg_quantity_per_day.csv");
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outputPath.toFile())))) {
            writer.write("DayOfWeek,ItemID,AvgQuantity");
            writer.newLine();
            for (Map.Entry<DayOfWeek, Map<Integer, Double>> dayEntry : quantityPerDayMap.entrySet()) {
                DayOfWeek day = dayEntry.getKey();
                for (Map.Entry<Integer, Double> itemEntry : dayEntry.getValue().entrySet()) {
                    int itemId = itemEntry.getKey();
                    double avgQuantity = itemEntry.getValue();
                    writer.write(day + "," + itemId + "," + avgQuantity);
                    writer.newLine();
                }
            }
        }
        logger.info("Avg Quantity Per Day of Week Report Generated Successfully");
    }
}
