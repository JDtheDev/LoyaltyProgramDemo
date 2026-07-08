package com.retail.loyalty.repository;

import com.retail.loyalty.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    Optional<Purchase> findByPurchaseReference(String purchaseReference);
    @Query("""
        SELECT COALESCE(SUM(p.amount), 0)
        FROM Purchase p
        WHERE p.customerId = :customerId
          AND p.refunded = false
          AND p.purchasedAt >= :windowStart
        """)
    double sumSpendSince(@Param("customerId") Long customerId, @Param("windowStart") LocalDateTime windowStart);

    @Query("""
        SELECT COALESCE(SUM(p.points), 0)
        FROM Purchase p
        WHERE p.customerId = :customerId
          AND p.refunded = false
          AND p.purchasedAt >= :windowStart
        """)
    int getAvailableBalance(@Param("customerId") Long customerId, @Param("windowStart") LocalDateTime windowStart);


    /**
     * Purchases with unspent, unexpired points for a customer within a
     * 12-month window, oldest first so redemption can walk this list and
     * consume from the earliest transactions first.
     */
    List<Purchase> findByCustomerIdAndPurchasedAtAfterAndRefundedFalseAndPointsGreaterThanOrderByPurchasedAtAsc(
            Long customerId, LocalDateTime windowStart, int minPoints
    );
}
