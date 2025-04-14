package com.cognitree.internship.report_gen.reports;

import com.cognitree.internship.report_gen.BuyRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class HourlyActivityReportTest {

    private final File outputDir = Path.of("reports", "temp").toFile();

    @BeforeEach
    void cleanOutputDir() {
        if (outputDir.exists()) {
            for (File file : Objects.requireNonNull(outputDir.listFiles())) {
                file.delete();
            }
        } else {
            outputDir.mkdirs();
        }
    }

    @Test
    void testGenerateReport() throws IOException {
        HourlyActivityReport hourlyActivityReport = new HourlyActivityReport();
        List<BuyRecord> records = new ArrayList<>();
        records.add(new BuyRecord(1, "2022-01-01T00:00:00Z", 101, 10, 1));
        records.add(new BuyRecord(2, "2022-01-01T01:00:00Z", 101, 20, 2));
        records.add(new BuyRecord(3, "2022-01-02T00:00:00Z", 201, 30, 3));
        records.add(new BuyRecord(4, "2022-01-02T01:00:00Z", 201, 40, 4));
        hourlyActivityReport.generateReport(records, outputDir.getAbsolutePath());
        assertTrue(new File(String.valueOf(Path.of(outputDir.toURI()).resolve("report_hourly_activity.csv"))).exists());
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf(Path.of(outputDir.toURI())
                .resolve("report_hourly_activity.csv"))))) {
            String line = bufferedReader.readLine();
            assertEquals("Hour,AvgActiveSessions,AvgUniqueItems", line);
            line = bufferedReader.readLine();
            assertEquals("0,1.0,1.0", line);
            line = bufferedReader.readLine();
            assertEquals("1,1.0,1.0", line);
            line = bufferedReader.readLine();
            assertEquals("2,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("3,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("4,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("5,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("6,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("7,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("8,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("9,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("10,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("11,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("12,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("13,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("14,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("15,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("16,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("17,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("18,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("19,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("20,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("21,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("22,0,0", line);
            line = bufferedReader.readLine();
            assertEquals("23,0,0", line);
            line = bufferedReader.readLine();
            assertNull(line);
        }
    }

    @Test
    void testGetName() {
        HourlyActivityReport hourlyActivityReport = new HourlyActivityReport();
        assertEquals("hourly_activity", hourlyActivityReport.getName());
    }

    @AfterEach
    void deleteGeneratedReports() {
        for (File file : Objects.requireNonNull(outputDir.listFiles())) {
            file.delete();
        }
        outputDir.delete();
    }
}