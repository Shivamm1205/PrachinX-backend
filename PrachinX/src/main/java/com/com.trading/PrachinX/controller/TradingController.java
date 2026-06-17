package com.com.trading.PrachinX.controller;

import com.com.trading.PrachinX.dto.request.TradeRequest;
import com.com.trading.PrachinX.dto.response.ApiResponse;
import com.com.trading.PrachinX.entity.Trade;
import com.com.trading.PrachinX.service.TradingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trades")
@CrossOrigin(origins = "*")
public class TradingController {

    @Autowired
    private TradingService tradingService;

    @PostMapping("/execute")
    public ResponseEntity<ApiResponse<Trade>> executeTrade(
            @Valid @RequestBody TradeRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Trade trade = tradingService.executeTrade(
                request, userDetails.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success("Trade executed successfully", trade));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Trade>>> getUserTrades(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<Trade> trades = tradingService.getUserTrades(
                userDetails.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success("Trades fetched successfully", trades));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Trade>> getTradeById(
            @PathVariable Long id) {
        Trade trade = tradingService.getTradeById(id);
        return ResponseEntity.ok(
                ApiResponse.success("Trade fetched successfully", trade));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Trade>> cancelTrade(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        Trade trade = tradingService.cancelTrade(
                id, userDetails.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success("Trade cancelled successfully", trade));
    }
}
