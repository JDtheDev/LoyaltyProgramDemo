package com.retail.loyalty.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String externalId) {
        super(String.format("No customer found with id '%s'", externalId));
    }
}
