package com.cognitree.internship.yoochoose_purchase_data_analysis.reports;

import com.cognitree.internship.yoochoose_purchase_data_analysis.PurchaseDataParser;

public interface Report {
    void generateReport(String outputDir);

    void init(PurchaseDataParser dataParser);
}
