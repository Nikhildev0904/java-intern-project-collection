package com.cognitree.internship.report_gen.reports;

import com.cognitree.internship.report_gen.BuyRecord;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseCountReportTest {

    @Test
    void testGenerateReport() throws IOException {
        PurchaseCountReport purchaseCountReport = new PurchaseCountReport();
        List<BuyRecord> records = new ArrayList<>();
        records.add(new BuyRecord(1, "2022-01-01T00:00:00Z", 101, 10, 1));
        records.add(new BuyRecord(2, "2022-01-01T01:00:00Z", 101, 20, 2));
        records.add(new BuyRecord(3, "2022-01-02T00:00:00Z", 201, 30, 3));
        records.add(new BuyRecord(4, "2022-01-02T01:00:00Z", 201, 40, 4));
        File outputDir = new File("reports/temp/");
        if (outputDir.exists()) {
            for (File file : Objects.requireNonNull(outputDir.listFiles())) {
                file.delete();
            }
        } else {
            outputDir.mkdirs();
        }
        purchaseCountReport.generateReport(records, "reports/temp/");
        assertTrue(new File("reports/temp/report_purchase_count.csv").exists());
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("reports/temp/report_purchase_count.csv"))) {
            String line = bufferedReader.readLine();
            assertEquals("ItemId,PurchaseEventCount", line);
            line = bufferedReader.readLine();
            assertEquals("101,2", line);
            line = bufferedReader.readLine();
            assertEquals("201,2", line);
            line = bufferedReader.readLine();
            assertNull(line);
        }
    }

    @Test
    void testGetName() {
        PurchaseCountReport purchaseCountReport = new PurchaseCountReport();
        assertEquals("purchase_count", purchaseCountReport.getName());
    }
}