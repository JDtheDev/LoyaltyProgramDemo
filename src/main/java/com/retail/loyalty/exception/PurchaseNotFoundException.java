package com.retail.loyalty.exception;

public class PurchaseNotFoundException extends RuntimeException {
    public PurchaseNotFoundException(String purchaseReference) {
        super(String.format("No purchase found with reference '%s'", purchaseReference));
    }
}
