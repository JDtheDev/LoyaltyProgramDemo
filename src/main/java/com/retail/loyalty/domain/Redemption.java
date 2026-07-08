package com.retail.loyalty.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "redemptions")
public class Redemption
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long rewardId;

    @Column(nullable = false)
    private int pointsSpent;

    @Column(nullable = false)
    private LocalDateTime redeemedAt;

    protected Redemption() {
    }

    public Redemption(Long customerId, Long rewardId, int pointsSpent, LocalDateTime redeemedAt) {
        this.customerId = customerId;
        this.rewardId = rewardId;
        this.pointsSpent = pointsSpent;
        this.redeemedAt = redeemedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getRewardId() {
        return rewardId;
    }

    public int getPointsSpent() {
        return pointsSpent;
    }

    public LocalDateTime getRedeemedAt() {
        return redeemedAt;
    }
}
