package com.retail.loyalty.exception;

public class InsufficientPointsException extends RuntimeException {
    public InsufficientPointsException(int available, int required) {
        super(String.format("Insufficient points: have '%s' but need '%s'", available, required));
    }
}
