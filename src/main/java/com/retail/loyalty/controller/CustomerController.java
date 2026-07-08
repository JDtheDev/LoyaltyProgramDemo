package com.retail.loyalty.controller;

import com.retail.loyalty.dto.CustomerResponse;
import com.retail.loyalty.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public List<CustomerResponse> listCustomers() {
        return customerService.listCustomers();
    }
}
