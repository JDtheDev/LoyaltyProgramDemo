package com.retail.loyalty.service;

import com.retail.loyalty.domain.Purchase;
import com.retail.loyalty.dto.RefundResponse;
import com.retail.loyalty.exception.AlreadyRefundedException;
import com.retail.loyalty.exception.PurchaseCannotBeRefundedException;
import com.retail.loyalty.exception.PurchaseNotFoundException;
import com.retail.loyalty.repository.PurchaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RefundService {

    private final PurchaseRepository purchaseRepository;
    private final PointsService pointsService;

    public RefundService(PurchaseRepository purchaseRepository,
                          PointsService pointsService) {
        this.purchaseRepository = purchaseRepository;
        this.pointsService = pointsService;
    }

    @Transactional
    public RefundResponse refund(String purchaseReference) {
        Purchase purchase = purchaseRepository.findByPurchaseReference(purchaseReference)
            .orElseThrow(() -> new PurchaseNotFoundException(purchaseReference));

        // Check if its already refunded
        if (purchase.isRefunded())
            throw new AlreadyRefundedException(purchaseReference);

        // Ensure no points were used as part of a redemption before refunding
        if (purchase.getAmount().intValue() != purchase.getPoints())
            throw new PurchaseCannotBeRefundedException(purchaseReference);

        // Otherwise
        purchase.markRefunded();

        int newBalance = pointsService.getAvailableBalance(purchase.getCustomerId());
        return new RefundResponse(purchaseReference, purchase.getPoints(), newBalance);
    }
}
