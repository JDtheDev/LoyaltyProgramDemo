package com.retail.loyalty.dto;

import com.retail.loyalty.enums.LoyaltyTier;

public record CustomerResponse(
    String externalId,
    String displayName,
    LoyaltyTier tier
) {
}
