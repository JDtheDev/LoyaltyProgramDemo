package com.retail.loyalty.service;

import com.retail.loyalty.domain.Customer;
import com.retail.loyalty.dto.BalanceResponse;
import com.retail.loyalty.exception.CustomerNotFoundException;
import com.retail.loyalty.repository.CustomerRepository;
import com.retail.loyalty.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PointsService {

    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;
    private final TierService tierService;

    public PointsService(CustomerRepository customerRepository,
                          PurchaseRepository purchaseRepository,
                          TierService tierService) {
        this.customerRepository = customerRepository;
        this.purchaseRepository = purchaseRepository;
        this.tierService = tierService;
    }

    public BalanceResponse getBalance(String customerExternalId) {
        Customer customer = customerRepository.findByExternalId(customerExternalId)
            .orElseThrow(() -> new CustomerNotFoundException(customerExternalId));

        int balance = purchaseRepository.getAvailableBalance(customer.getId(), LocalDateTime.now().minusMonths(12));
        var tier = tierService.getTier(customer.getId());

        return new BalanceResponse(customerExternalId, balance, tier);
    }

    /** Used internally by RedemptionService, which needs the raw int rather than the full DTO. */
    public int getAvailableBalance(Long customerId) {
        return purchaseRepository.getAvailableBalance(customerId, LocalDateTime.now().minusMonths(12));
    }
}
