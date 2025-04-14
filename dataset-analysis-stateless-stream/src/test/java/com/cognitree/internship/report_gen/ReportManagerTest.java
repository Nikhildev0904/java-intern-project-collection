package com.cognitree.internship.report_gen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;

class ReportManagerTest {

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
    void testGenerateAllReports() throws IOException {
        ReportManager reportManager = new ReportManager(Path.of("src", "test", "resources", "test_data.csv").toString(),
                outputDir.getAbsolutePath());
        reportManager.generateAllReports();
        long expectedReportCount = ServiceLoader.load(Report.class).stream().count();
        File[] generatedFiles = outputDir.listFiles();
        assertNotNull(generatedFiles);
        assertEquals(expectedReportCount, generatedFiles.length);
    }

    @Test
    void testGenerateReport() throws IOException {
        ReportManager reportManager = new ReportManager(Path.of("src", "test", "resources", "test_data.csv").toString(),
                outputDir.getAbsolutePath());
        reportManager.generateReport("purchase_count");
        File expectedFile = new File(outputDir, "report_purchase_count.csv");
        assertTrue(expectedFile.exists());
        reportManager.generateReport("non_existent_report");
        File nonExistentFile = new File(outputDir, "report_non_existent_report.csv");
        assertFalse(nonExistentFile.exists());
    }

    @AfterEach
    void deleteGeneratedReports() {
        for (File file : Objects.requireNonNull(outputDir.listFiles())) {
            file.delete();
        }
        outputDir.delete();
    }
}