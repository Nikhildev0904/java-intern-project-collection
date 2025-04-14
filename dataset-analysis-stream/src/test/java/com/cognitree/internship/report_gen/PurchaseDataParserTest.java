package com.cognitree.internship.report_gen;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseDataParserTest {

    @Test
    void testParseRawData() throws IOException {
        String path = Paths.get("src/test/resources", "test_data.csv").toAbsolutePath().toString();
        PurchaseDataParser purchaseDataParser = new PurchaseDataParser();
        Consumer<BuyRecord> consumer = record -> {
            if (record.sessionID() == 1) {
                assertEquals(101, record.itemID());
                assertEquals(10, record.price());
                assertEquals(1, record.quantity());
            } else if (record.sessionID() == 2) {
                assertEquals(201, record.itemID());
                assertEquals(20, record.price());
                assertEquals(2, record.quantity());
            }
        };
        purchaseDataParser.parseRawData(path, consumer);
    }
}