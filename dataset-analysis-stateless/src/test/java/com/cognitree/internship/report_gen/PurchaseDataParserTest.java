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
        assertEquals(6, purchaseDataParser.parseRawData(path).size());
        BuyRecord record1 = purchaseDataParser.parseRawData(path).get(0);
        assertEquals(1, record1.sessionID());
        assertEquals(101, record1.itemID());
        assertEquals(10, record1.price());
        assertEquals(1, record1.quantity());
        BuyRecord record2 = purchaseDataParser.parseRawData(path).get(1);
        assertEquals(2, record2.sessionID());
        assertEquals(201, record2.itemID());
        assertEquals(20, record2.price());
        assertEquals(2, record2.quantity());
    }
}