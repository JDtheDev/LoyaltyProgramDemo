package com.retail.loyalty.repository;
import com.retail.loyalty.domain.Redemption;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RedemptionRepository extends JpaRepository<Redemption, Long>
{
    // Redemption history for a customer, most recent first.
    List<Redemption> findByCustomerIdOrderByRedeemedAtDesc(Long customerId);
}
