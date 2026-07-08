package com.retail.loyalty.dto;
import java.math.BigDecimal;

// POST /customers/{customerId}/purchases
public record PurchaseRequest(
        String purchaseReference,
        BigDecimal amount
) {
}
