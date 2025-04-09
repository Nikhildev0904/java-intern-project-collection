package com.cognitree.internship.report_gen.reports;

import com.cognitree.internship.report_gen.BuyRecord;
import com.cognitree.internship.report_gen.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class HourlyActivityReport implements Report {
    private static final Logger log = LoggerFactory.getLogger(HourlyActivityReport.class);

    @Override
    public void generateReport(List<BuyRecord> records, String outputDir) throws IOException {
        Map<Integer, Map<LocalDate, Set<Integer>>> hourlySessions = new HashMap<>();
        Map<Integer, Map<LocalDate, Set<Integer>>> hourlyItems = new HashMap<>();
        aggregateRecords(records, hourlySessions, hourlyItems);
        writeReport(outputDir, hourlySessions, hourlyItems);
    }

    @Override
    public String getName() {
        return "hourly_activity";
    }

    private void aggregateRecords(List<BuyRecord> records, Map<Integer, Map<LocalDate, Set<Integer>>> hourlySessions,
                                  Map<Integer, Map<LocalDate, Set<Integer>>> hourlyItems) {
        hourlySessions.putAll(records.stream().
                collect(Collectors.groupingBy(record -> {
                            LocalDateTime hour = LocalDateTime.ofInstant(Instant.parse(record.timeStamp()), ZoneOffset.UTC);
                            return hour.getHour();
                        },
                        Collectors.groupingBy(record -> {
                                    LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.parse(record.timeStamp()), ZoneOffset.UTC);
                                    return dateTime.toLocalDate();
                                },
                                Collectors.mapping(BuyRecord::sessionID, Collectors.toSet())
                        )
                )));
        hourlyItems.putAll(records.stream().
                collect(Collectors.groupingBy(record -> {
                            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.parse(record.timeStamp()), ZoneOffset.UTC);
                            return dateTime.getHour();
                        },
                        Collectors.groupingBy(record -> {
                                    LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.parse(record.timeStamp()), ZoneOffset.UTC);
                                    return dateTime.toLocalDate();
                                },
                                Collectors.mapping(BuyRecord::itemID, Collectors.toSet())
                        )
                )));
    }

    private void writeReport(String outputDir, Map<Integer, Map<LocalDate, Set<Integer>>> hourlySessions,
                             Map<Integer, Map<LocalDate, Set<Integer>>> hourlyItems) throws IOException {
        Path outputPath = Paths.get(outputDir, "report_hourly_activity.csv");
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outputPath.toFile())))) {
            writer.write("Hour,AvgActiveSessions,AvgUniqueItems");
            writer.newLine();
            for (int hour = 0; hour < 24; hour++) {
                Map<LocalDate, Set<Integer>> daySessionMap = hourlySessions.get(hour);
                Map<LocalDate, Set<Integer>> dayItemMap = hourlyItems.get(hour);
                if (daySessionMap == null || dayItemMap == null) {
                    writer.write(hour + ",0,0");
                    writer.newLine();
                    continue;
                }
                double totalSessions = 0;
                double totalItems = 0;
                int dayCount = daySessionMap.size();
                for (Set<Integer> sessions : daySessionMap.values()) {
                    totalSessions += sessions.size();
                }
                for (Set<Integer> items : dayItemMap.values()) {
                    totalItems += items.size();
                }
                double avgSessions = totalSessions / dayCount;
                double avgItems = totalItems / dayCount;
                writer.write(hour + "," + avgSessions + "," + avgItems);
                writer.newLine();
            }
        }
        log.info("Hourly Activity Report Generated Successfully");
    }
}
