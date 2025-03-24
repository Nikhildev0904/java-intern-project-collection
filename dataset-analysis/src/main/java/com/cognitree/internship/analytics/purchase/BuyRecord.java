package com.cognitree.internship.analytics.purchase;

public record BuyRecord(int sessionID, String timeStamp, int itemID, int price, int quantity) {
}
