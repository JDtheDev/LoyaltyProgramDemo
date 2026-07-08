package com.retail.loyalty.exception;

public class RewardNotFoundException extends RuntimeException {
    public RewardNotFoundException(String rewardCode) {
        super(String.format("No reward found with code '%s'", rewardCode));
    }
}
