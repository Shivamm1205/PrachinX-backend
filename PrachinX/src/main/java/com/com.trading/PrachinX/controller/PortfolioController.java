package com.com.trading.PrachinX.controller;

import com.com.trading.PrachinX.dto.response.ApiResponse;
import com.com.trading.PrachinX.entity.Portfolio;
import com.com.trading.PrachinX.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolio")
@CrossOrigin(origins = "*")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<ApiResponse<Portfolio>> getPortfolio(
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Portfolio portfolio = portfolioService
                    .getOrCreatePortfolio(userDetails.getUsername());
            return ResponseEntity.ok(
                    ApiResponse.success("Portfolio fetched", portfolio));
        } catch (Exception e) {
            return ResponseEntity.ok(
                    ApiResponse.success("No portfolio yet", null));
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse<Portfolio>> deposit(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, Double> request) {
        BigDecimal amount = BigDecimal.valueOf(request.get("amount"));
        Portfolio portfolio = portfolioService.deposit(
                userDetails.getUsername(), amount);
        return ResponseEntity.ok(
                ApiResponse.success("Deposit successful", portfolio));
    }
}