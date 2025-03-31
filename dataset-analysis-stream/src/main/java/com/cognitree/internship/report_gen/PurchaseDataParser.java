package com.cognitree.internship.report_gen;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class PurchaseDataParser {

    public void parseRawData(String path, Consumer<BuyRecord> consumer) throws IOException {
        try (Stream<String> lines = Files.lines(Path.of(path))) {
            lines.map(line -> line.split(","))
                    .filter(line -> line.length == 5)
                    .forEach(fields -> {
                                int sessionID = Integer.parseInt(fields[0]);
                                String timeStamp = fields[1];
                                int itemID = Integer.parseInt(fields[2]);
                                int price = Integer.parseInt(fields[3]);
                                int quantity = Integer.parseInt(fields[4]);
                                BuyRecord record = new BuyRecord(sessionID, timeStamp, itemID, price, quantity);
                                consumer.accept(record);
                            }
                    );
        }
    }
}

