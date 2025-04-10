package com.cognitree.internship.report_gen;

import java.io.IOException;
import java.util.List;

public interface Report {

    void generateReport(List<BuyRecord> records, String outputDir) throws IOException;

    String getName();
}
