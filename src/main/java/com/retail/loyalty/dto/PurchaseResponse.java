package com.retail.loyalty.dto;

public record PurchaseResponse(
    String purchaseReference,
    int pointsEarned,
    int newBalance
) {
}
