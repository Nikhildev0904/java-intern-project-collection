package com.cognitree.internship.report_gen;

import java.io.IOException;

public interface Report {
    void generateReport(String outputDir) throws IOException;

    void addRecord(BuyRecord record);

    String getName();
}
