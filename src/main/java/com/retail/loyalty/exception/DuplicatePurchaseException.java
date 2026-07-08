package com.retail.loyalty.exception;

public class DuplicatePurchaseException extends RuntimeException {
    public DuplicatePurchaseException(String purchaseReference) {
        super(String.format("Purchase with reference '%s' has already been recorded", purchaseReference));
    }
}
