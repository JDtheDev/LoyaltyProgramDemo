package com.retail.loyalty.controller;

import com.retail.loyalty.dto.BalanceResponse;
import com.retail.loyalty.service.PointsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PointsController {

    private final PointsService pointsService;

    public PointsController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @GetMapping("/customers/{customerId}/balance")
    public BalanceResponse getBalance(@PathVariable String customerId) {
        return pointsService.getBalance(customerId);
    }
}
