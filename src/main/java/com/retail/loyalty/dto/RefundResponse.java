package com.retail.loyalty.dto;

public record RefundResponse(
    String purchaseReference,
    int pointsClawedBack,
    int newBalance
) {
}
