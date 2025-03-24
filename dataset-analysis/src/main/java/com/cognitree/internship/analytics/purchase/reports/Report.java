package com.cognitree.internship.analytics.purchase.reports;

import com.cognitree.internship.analytics.purchase.BuyRecord;

public interface Report {
    void generateReport(String outputDir);

    void addRecord(BuyRecord record);

    String getName();
}
