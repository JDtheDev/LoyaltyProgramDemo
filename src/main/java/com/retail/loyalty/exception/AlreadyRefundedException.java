package com.retail.loyalty.exception;

public class AlreadyRefundedException extends RuntimeException {
    public AlreadyRefundedException(String purchaseReference) {
        super(String.format("Purchase '%s' has already been refunded", purchaseReference));
    }
}
