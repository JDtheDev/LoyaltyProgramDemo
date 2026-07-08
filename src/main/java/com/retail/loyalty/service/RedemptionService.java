package com.retail.loyalty.service;

import com.retail.loyalty.domain.Customer;
import com.retail.loyalty.domain.Purchase;
import com.retail.loyalty.domain.Redemption;
import com.retail.loyalty.domain.Reward;
import com.retail.loyalty.dto.RedemptionHistory;
import com.retail.loyalty.dto.RedemptionResponse;
import com.retail.loyalty.exception.CustomerNotFoundException;
import com.retail.loyalty.exception.InsufficientPointsException;
import com.retail.loyalty.exception.RewardNotFoundException;
import com.retail.loyalty.repository.CustomerRepository;
import com.retail.loyalty.repository.PurchaseRepository;
import com.retail.loyalty.repository.RedemptionRepository;
import com.retail.loyalty.repository.RewardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RedemptionService {

    private final CustomerRepository customerRepository;
    private final RewardRepository rewardRepository;
    private final RedemptionRepository redemptionRepository;
    private final PurchaseRepository purchaseRepository;
    private final PointsService pointsService;

    public RedemptionService(CustomerRepository customerRepository,
                              RewardRepository rewardRepository,
                              RedemptionRepository redemptionRepository,
                              PurchaseRepository purchaseRepository,
                              PointsService pointsService) {
        this.customerRepository = customerRepository;
        this.rewardRepository = rewardRepository;
        this.redemptionRepository = redemptionRepository;
        this.purchaseRepository = purchaseRepository;
        this.pointsService = pointsService;
    }

    @Transactional
    public RedemptionResponse redeem(String customerExternalId, String rewardCode) {
        Customer customer = customerRepository.findByExternalId(customerExternalId)
            .orElseThrow(() -> new CustomerNotFoundException(customerExternalId));

        Reward reward = rewardRepository.findByCode(rewardCode)
            .orElseThrow(() -> new RewardNotFoundException(rewardCode));

        int cost = reward.getPointsCost();
        int available = pointsService.getAvailableBalance(customer.getId());
        if (cost > available) {
            throw new InsufficientPointsException(available, cost);
        }

        redeemPoints(customer.getId(), cost);

        Redemption redemption = redemptionRepository.save(
            new Redemption(customer.getId(), reward.getId(), cost, LocalDateTime.now())
        );

        int remainingBalance = pointsService.getAvailableBalance(customer.getId());
        return new RedemptionResponse(reward.getCode(), redemption.getPointsSpent(), remainingBalance);
    }

    public List<RedemptionHistory> getHistory(String customerExternalId) {
        Customer customer = customerRepository.findByExternalId(customerExternalId)
                .orElseThrow(() -> new CustomerNotFoundException(customerExternalId));

        return redemptionRepository.findByCustomerIdOrderByRedeemedAtDesc(customer.getId()).stream()
                .map(redemption -> {
                    Reward reward = rewardRepository.findById(redemption.getRewardId())
                            .orElseThrow(() -> new RewardNotFoundException("id " + redemption.getRewardId()));
                    return new RedemptionHistory(
                            reward.getCode(),
                            reward.getName(),
                            redemption.getPointsSpent(),
                            redemption.getRedeemedAt()
                    );
                })
                .toList();
    }

    // Find the oldest purchases for this user and deduct points starting with those points
    @Transactional
    public void redeemPoints(Long customerId, int cost) {
        LocalDateTime windowStart = LocalDateTime.now().minusMonths(12);
        List<Purchase> eligiblePurchases = purchaseRepository
                .findByCustomerIdAndPurchasedAtAfterAndRefundedFalseAndPointsGreaterThanOrderByPurchasedAtAsc(
                        customerId, windowStart, 0
                );

        int remainingToConsume = cost;
        for (Purchase purchase : eligiblePurchases) {
            if (remainingToConsume == 0) {
                break;
            }
            int consumedFromThisPurchase = Math.min(purchase.getPoints(), remainingToConsume);
            purchase.deductPoints(consumedFromThisPurchase);
            purchaseRepository.save(purchase);
            remainingToConsume -= consumedFromThisPurchase;
        }

        if (remainingToConsume > 0) {
            throw new InsufficientPointsException(cost - remainingToConsume, cost);
        }
    }
}
