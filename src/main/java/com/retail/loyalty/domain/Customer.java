package com.retail.loyalty.domain;

import com.retail.loyalty.enums.LoyaltyTier;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * A customer enrolled in the loyalty program.
 *
 * Deliberately thin: balance and tier are DERIVED values computed from the
 * PointsLedgerEntry and Purchase tables at read time, not stored here. That
 * avoids the classic bug where a cached "balance" column drifts out of sync
 * with the ledger it's supposed to summarize.
 */
@Entity
@Table(name = "customers")
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String externalId;
    private String displayName;
    private LoyaltyTier tier;

    protected Customer() {
    }

    public Customer(String externalId, String displayName) {
        this.externalId = externalId;
        this.displayName = displayName;
    }

    public Long getId() {
        return id;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
