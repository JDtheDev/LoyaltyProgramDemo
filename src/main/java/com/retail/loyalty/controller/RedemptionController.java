package com.retail.loyalty.controller;

import com.retail.loyalty.dto.RedemptionHistory;
import com.retail.loyalty.dto.RedemptionRequest;
import com.retail.loyalty.dto.RedemptionResponse;
import com.retail.loyalty.service.RedemptionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RedemptionController {

    private final RedemptionService redemptionService;

    public RedemptionController(RedemptionService redemptionService) {
        this.redemptionService = redemptionService;
    }

    @PostMapping("/customers/{customerId}/redemptions")
    @ResponseStatus(HttpStatus.CREATED)
    public RedemptionResponse redeem(@PathVariable String customerId, @RequestBody RedemptionRequest request) {
        return redemptionService.redeem(customerId, request.rewardCode());
    }

    @GetMapping("/customers/{customerId}/redemptions")
    public List<RedemptionHistory> getRedemptionHistory(@PathVariable String customerId) {
        return redemptionService.getHistory(customerId);
    }
}
