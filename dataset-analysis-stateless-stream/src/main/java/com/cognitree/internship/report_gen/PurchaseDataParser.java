package com.cognitree.internship.report_gen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class PurchaseDataParser {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseDataParser.class);

    public List<BuyRecord> parseRawData(String path) throws IOException {
        logger.info("Parsing the input file: {}", path);
        List<BuyRecord> records = new ArrayList<>();
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
        }
        return records;
    }
}
