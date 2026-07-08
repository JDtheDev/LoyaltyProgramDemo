package com.retail.loyalty.controller;

import com.retail.loyalty.dto.PurchaseRequest;
import com.retail.loyalty.dto.PurchaseResponse;
import com.retail.loyalty.dto.RefundResponse;
import com.retail.loyalty.service.PurchaseService;
import com.retail.loyalty.service.RefundService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final RefundService refundService;

    public PurchaseController(PurchaseService purchaseService, RefundService refundService) {
        this.purchaseService = purchaseService;
        this.refundService = refundService;
    }

    @PostMapping("/customers/{customerId}/purchases")
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseResponse recordPurchase(@PathVariable String customerId, @RequestBody PurchaseRequest request) {
        return purchaseService.recordPurchase(customerId, request.purchaseReference(), request.amount());
    }

    @PostMapping("/purchases/{purchaseReference}/refund")
    public RefundResponse refundPurchase(@PathVariable String purchaseReference) {
        return refundService.refund(purchaseReference);
    }
}
