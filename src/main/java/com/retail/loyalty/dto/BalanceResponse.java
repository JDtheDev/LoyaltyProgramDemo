package com.retail.loyalty.dto;

import com.retail.loyalty.enums.LoyaltyTier;

public record BalanceResponse(
        String customerId,
        int availablePoints,
        LoyaltyTier tier
) {
}
