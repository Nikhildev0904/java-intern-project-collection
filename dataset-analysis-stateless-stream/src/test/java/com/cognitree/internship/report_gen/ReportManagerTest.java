package com.cognitree.internship.report_gen;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ReportManagerTest {

    @Test
    void testGenerateAllReports() throws IOException {
        ReportManager reportManager = new ReportManager("src/test/resources/test_data.csv", "reports/temp/");
        File outputDir = new File("reports/temp/");
        if (outputDir.exists()) {
            for (File file : Objects.requireNonNull(outputDir.listFiles())) {
                file.delete();
            }
        } else {
            outputDir.mkdirs();
        }
        reportManager.generateAllReports();
        assertEquals(5, Objects.requireNonNull(new File("reports/temp/").listFiles()).length);
    }

    @Test
    void testGenerateReport() throws IOException {
        ReportManager reportManager = new ReportManager("src/test/resources/test_data.csv", "reports/temp/");
        File outputDir = new File("reports/temp/");
        if (outputDir.exists()) {
            for (File file : Objects.requireNonNull(outputDir.listFiles())) {
                file.delete();
            }
        } else {
            outputDir.mkdirs();
        }
        reportManager.generateReport("purchase_count");
        assertTrue(new File("reports/temp/report_purchase_count.csv").exists());
        reportManager.generateReport("non_existent_report");
        assertFalse(new File("reports/temp/report_non_existent_report.csv").exists());
    }
}