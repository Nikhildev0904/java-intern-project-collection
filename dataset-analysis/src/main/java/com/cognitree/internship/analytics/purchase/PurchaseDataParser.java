package com.cognitree.internship.analytics.purchase;

import java.io.*;
import java.util.*;

public class PurchaseDataParser {
    private final List<BuyRecord> records;

    public PurchaseDataParser(String path) {
        this.records = new ArrayList<>();
        parseRawData(path);
    }

    public List<BuyRecord> getRecords() {
        return records;
    }

    private void parseRawData(String path) {
        try (FileInputStream fileInputStream = new FileInputStream(path);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(",");
                int sessionID = Integer.parseInt(fields[0]);
                String timeStamp = fields[1];
                int itemID = Integer.parseInt(fields[2]);
                int price = Integer.parseInt(fields[3]);
                int quantity = Integer.parseInt(fields[4]);
                BuyRecord record = new BuyRecord(sessionID, timeStamp, itemID, price, quantity);
                records.add(record);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error parsing file: ", e);
        }
    }

}
