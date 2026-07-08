package com.retail.loyalty.dto;

import java.time.LocalDateTime;

public record RedemptionHistory(
        String rewardCode,
        String rewardName,
        int pointsSpent,
        LocalDateTime redeemedAt
) {
}