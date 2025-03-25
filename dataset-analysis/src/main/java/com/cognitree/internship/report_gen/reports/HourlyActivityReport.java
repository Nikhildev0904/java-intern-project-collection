package com.cognitree.internship.report_gen.reports;

import com.cognitree.internship.report_gen.BuyRecord;

import java.io.*;
import java.time.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HourlyActivityReport implements Report {
    private final Map<Integer, Map<LocalDate, Set<Integer>>> hourlySessions = new HashMap<>();
    private final Map<Integer, Map<LocalDate, Set<Integer>>> hourlyItems = new HashMap<>();

    @Override
    public void generateReport(String outputDir) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outputDir + "/report_hourly_activity.csv")))) {
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
        System.out.println("Hourly Activity Report Generated Successfully");
    }

    @Override
    public void addRecord(BuyRecord record) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.parse(record.timeStamp()), ZoneOffset.UTC);
        int hour = dateTime.getHour();
        LocalDate date = dateTime.toLocalDate();
        int sessionId = record.sessionID();
        int itemId = record.itemID();
        hourlySessions.computeIfAbsent(hour, k -> new HashMap<>())
                .computeIfAbsent(date, d -> new HashSet<>())
                .add(sessionId);
        hourlyItems.computeIfAbsent(hour, k -> new HashMap<>())
                .computeIfAbsent(date, d -> new HashSet<>())
                .add(itemId);
    }

    @Override
    public String getName() {
        return "HourlyActivityReport";
    }
}
