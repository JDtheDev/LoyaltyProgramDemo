package com.retail.loyalty.service;

import com.retail.loyalty.enums.LoyaltyTier;
import com.retail.loyalty.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TierService {

    private static final double SILVER_THRESHOLD = 100;
    private static final double GOLD_THRESHOLD = 1000;
    private static final double PLATINUM_THRESHOLD = 5000;

    private final PurchaseRepository purchaseRepository;

    public TierService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public LoyaltyTier getTier(Long customerId) {
        LocalDateTime windowStart = LocalDateTime.now().minusMonths(12);
        double rollingSpend = purchaseRepository.sumSpendSince(customerId, windowStart);

        if (rollingSpend > PLATINUM_THRESHOLD)
            return LoyaltyTier.PLATINUM;
        if (rollingSpend > GOLD_THRESHOLD)
            return LoyaltyTier.GOLD;
        if (rollingSpend > SILVER_THRESHOLD)
            return LoyaltyTier.SILVER;
        return LoyaltyTier.NONE;
    }
}
