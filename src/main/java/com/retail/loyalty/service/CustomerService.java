package com.retail.loyalty.service;

import com.retail.loyalty.dto.CustomerResponse;
import com.retail.loyalty.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final TierService tierService;

    public CustomerService(CustomerRepository customerRepository, TierService tierService) {
        this.customerRepository = customerRepository;
        this.tierService = tierService;
    }

    public List<CustomerResponse> listCustomers() {
        return customerRepository.findAll().stream()
                .map(customer -> new CustomerResponse(
                        customer.getExternalId(),
                        customer.getDisplayName(),
                        tierService.getTier(customer.getId())
                ))
                .toList();
    }
}
