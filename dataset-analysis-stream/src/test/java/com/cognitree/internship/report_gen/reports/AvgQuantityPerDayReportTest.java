package com.cognitree.internship.report_gen.reports;

import com.cognitree.internship.report_gen.BuyRecord;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AvgQuantityPerDayReportTest {

    @Test
    void testGenerateReport() throws IOException {
        AvgQuantityPerDayReport avgQuantityPerDayReport = new AvgQuantityPerDayReport();
        avgQuantityPerDayReport.addRecord(new BuyRecord(1, "2022-01-01T00:00:00Z", 10, 1, 101));
        avgQuantityPerDayReport.addRecord(new BuyRecord(2, "2022-01-01T01:00:00Z", 20, 2, 101));
        avgQuantityPerDayReport.addRecord(new BuyRecord(3, "2022-01-02T00:00:00Z", 30, 3, 201));
        avgQuantityPerDayReport.addRecord(new BuyRecord(4, "2022-01-02T01:00:00Z", 40, 4, 201));
        File outputDir = new File("reports/temp/");
        if (outputDir.exists()) {
            for (File file : Objects.requireNonNull(outputDir.listFiles())) {
                file.delete();
            }
        } else {
            outputDir.mkdirs();
        }
        avgQuantityPerDayReport.generateReport("reports/temp/");
        assertTrue(new File("reports/temp/report_avg_quantity_per_day.csv").exists());
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("reports/temp/report_avg_quantity_per_day.csv"))) {
            String line = bufferedReader.readLine();
            assertEquals("DayOfWeek,ItemID,AvgQuantity", line);
            line = bufferedReader.readLine();
            assertEquals("SUNDAY,40,201.0", line);
            line = bufferedReader.readLine();
            assertEquals("SUNDAY,30,201.0", line);
            line = bufferedReader.readLine();
            assertEquals("SATURDAY,20,101.0", line);
            line = bufferedReader.readLine();
            assertEquals("SATURDAY,10,101.0", line);
            line = bufferedReader.readLine();
            assertNull(line);
        }
    }

    @Test
    void testGetName() {
        AvgQuantityPerDayReport avgQuantityPerDayReport = new AvgQuantityPerDayReport();
        assertEquals("avg_quantity_day", avgQuantityPerDayReport.getName());
    }
}