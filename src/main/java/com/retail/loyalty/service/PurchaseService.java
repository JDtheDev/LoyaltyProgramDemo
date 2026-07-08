package com.retail.loyalty.service;
import com.retail.loyalty.domain.Customer;
import com.retail.loyalty.domain.Purchase;
import com.retail.loyalty.dto.PurchaseResponse;
import com.retail.loyalty.exception.CustomerNotFoundException;
import com.retail.loyalty.exception.DuplicatePurchaseException;
import com.retail.loyalty.repository.CustomerRepository;
import com.retail.loyalty.repository.PurchaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class PurchaseService {

    private static final BigDecimal POINTS_PER_DOLLAR = BigDecimal.ONE;

    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;
    private final PointsService pointsService;

    public PurchaseService(CustomerRepository customerRepository,
                            PurchaseRepository purchaseRepository,
                            PointsService pointsService) {
        this.customerRepository = customerRepository;
        this.purchaseRepository = purchaseRepository;
        this.pointsService = pointsService;
    }

    @Transactional
    public PurchaseResponse recordPurchase(String customerExternalId, String purchaseReference, BigDecimal amount) {

        // Guard clauses
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Purchase amount must be positive");
        if (customerExternalId.isBlank()) throw new CustomerNotFoundException("Could not find customer");

        purchaseRepository.findByPurchaseReference(purchaseReference)
            .ifPresent(existing -> {
                throw new DuplicatePurchaseException(purchaseReference);
            });

        Customer customer = customerRepository.findByExternalId(customerExternalId)
            .orElseGet(() -> customerRepository.save(new Customer(customerExternalId, customerExternalId)));

        int pointsEarned = amount.multiply(POINTS_PER_DOLLAR)
                .setScale(0, RoundingMode.DOWN)
                .intValueExact();

        LocalDateTime now = LocalDateTime.now();
        Purchase purchase = purchaseRepository.save(
            new Purchase(customer.getId(), purchaseReference, amount, now, pointsEarned)
        );

        int newBalance = pointsService.getAvailableBalance(customer.getId());
        return new PurchaseResponse(purchaseReference, pointsEarned, newBalance);
    }
}
