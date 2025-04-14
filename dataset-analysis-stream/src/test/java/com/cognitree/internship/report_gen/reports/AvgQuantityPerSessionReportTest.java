package com.cognitree.internship.report_gen.reports;

import com.cognitree.internship.report_gen.BuyRecord;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AvgQuantityPerSessionReportTest {

    @Test
    void testGenerateReport() throws IOException {
        AvgQuantityPerSessionReport avgQuantityPerSessionReport = new AvgQuantityPerSessionReport();
        avgQuantityPerSessionReport.addRecord(new BuyRecord(1, "2022-01-01T00:00:00Z", 101, 10, 1));
        avgQuantityPerSessionReport.addRecord(new BuyRecord(2, "2022-01-01T01:00:00Z", 101, 20, 2));
        avgQuantityPerSessionReport.addRecord(new BuyRecord(3, "2022-01-02T00:00:00Z", 201, 30, 3));
        avgQuantityPerSessionReport.addRecord(new BuyRecord(4, "2022-01-02T01:00:00Z", 201, 40, 4));
        File outputDir = new File("reports/temp/");
        if (outputDir.exists()) {
            for (File file : Objects.requireNonNull(outputDir.listFiles())) {
                file.delete();
            }
        } else {
            outputDir.mkdirs();
        }
        avgQuantityPerSessionReport.generateReport("reports/temp/");
        assertTrue(new File("reports/temp/report_avg_quantity_per_session.csv").exists());
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("reports/temp/report_avg_quantity_per_session.csv"))) {
            String line = bufferedReader.readLine();
            assertEquals("ItemId,AvgQuantityPerSession", line);
            line = bufferedReader.readLine();
            assertEquals("101,1.5", line);
            line = bufferedReader.readLine();
            assertEquals("201,3.5", line);
            line = bufferedReader.readLine();
            assertNull(line);
        }
    }

    @Test
    void testGetName() {
        AvgQuantityPerSessionReport avgQuantityPerSessionReport = new AvgQuantityPerSessionReport();
        assertEquals("avg_quantity_session", avgQuantityPerSessionReport.getName());
    }
}