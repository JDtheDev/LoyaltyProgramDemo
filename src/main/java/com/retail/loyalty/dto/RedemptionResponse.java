package com.retail.loyalty.dto;

public record RedemptionResponse(
        String rewardCode,
        int pointsSpent,
        int remainingBalance
) {
}
