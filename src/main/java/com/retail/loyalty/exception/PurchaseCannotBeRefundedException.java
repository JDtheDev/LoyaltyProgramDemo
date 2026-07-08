package com.retail.loyalty.exception;

public class PurchaseCannotBeRefundedException extends RuntimeException {
    public PurchaseCannotBeRefundedException(String purchaseReference) {
        super(String.format("This purchase was part of a redemption and cannot be refunded: '%s'", purchaseReference));
    }
}