package com.retail.loyalty.dto;

// POST /customers/{customerId}/redemptions
public record RedemptionRequest(
        String rewardCode
) {
}
