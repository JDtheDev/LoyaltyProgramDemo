package com.retail.loyalty.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchases")
public class Purchase
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false, unique = true)
    private String purchaseReference;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = true)
    private Integer points;

    @Column(nullable = false)
    private LocalDateTime purchasedAt;

    private boolean refunded = false;

    protected Purchase() {
    }

    public Purchase(Long customerId, String purchaseReference, BigDecimal amount, LocalDateTime purchasedAt, Integer points) {
        this.customerId = customerId;
        this.purchaseReference = purchaseReference;
        this.amount = amount;
        this.purchasedAt = purchasedAt;
        this.points = points;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getPurchaseReference() {
        return purchaseReference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Integer getPoints() { return points; }

    public LocalDateTime getPurchasedAt() {
        return purchasedAt;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void markRefunded() {
        this.refunded = true;
    }

    public void deductPoints(int amount) {
        if (points == null || amount > points) {
            throw new IllegalStateException(
                    "Cannot deduct " + amount + " points; only " + points + " remaining on purchase " + purchaseReference
            );
        }
        this.points -= amount;
    }
}
