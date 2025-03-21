package com.cognitree.internship.yoochoose_purchase_data_analysis;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class PurchaseDataParser {
    private final Map<Integer, Integer> purchaseCountMap;
    private final Map<Integer, Set<Integer>> distinctSessionMap;
    private final Map<Integer, Integer> totalQuantityMap;

    public PurchaseDataParser(String path) {
        purchaseCountMap = new HashMap<>();
        distinctSessionMap = new HashMap<>();
        totalQuantityMap = new HashMap<>();
        parseRawData(path);
    }

    private void parseRawData(String path) {
        try (FileInputStream fileInputStream = new FileInputStream(path);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parse = line.split(",");
                int sessionID = Integer.parseInt(parse[0]);
                String timeStamp = parse[1];
                int itemID = Integer.parseInt(parse[2]);
                int price = Integer.parseInt(parse[3]);
                int quantity = Integer.parseInt(parse[4]);
                purchaseCountMap.put(itemID, purchaseCountMap.getOrDefault(itemID, 0) + 1);
                if (!distinctSessionMap.containsKey(itemID)) {
                    distinctSessionMap.put(itemID, new HashSet<>());
                }
                distinctSessionMap.get(itemID).add(sessionID);
                totalQuantityMap.put(itemID, totalQuantityMap.getOrDefault(itemID, 0) + quantity);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error processing file: ", e);
        }
    }

    public Map<Integer, Integer> getPurchaseCountMap() {
        return purchaseCountMap;
    }

    public Map<Integer, Set<Integer>> getDistinctSessionMap() {
        return distinctSessionMap;
    }

    public Map<Integer, Integer> getTotalQuantityMap() {
        return totalQuantityMap;
    }
}
