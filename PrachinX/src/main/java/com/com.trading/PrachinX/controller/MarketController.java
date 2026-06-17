package com.com.trading.PrachinX.controller;

import com.com.trading.PrachinX.dto.response.ApiResponse;
import com.com.trading.PrachinX.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/market")
@CrossOrigin(origins = "*")
public class MarketController {

    @Autowired
    private MarketService marketService;

    @GetMapping("/price/{symbol}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getPrice(
            @PathVariable String symbol) {
        Map<String, Object> price = marketService.getTickerPrice(symbol);
        return ResponseEntity.ok(
                ApiResponse.success("Price fetched successfully", price));
    }

    @GetMapping("/stats/{symbol}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats(
            @PathVariable String symbol) {
        Map<String, Object> stats = marketService.get24hrStats(symbol);
        return ResponseEntity.ok(
                ApiResponse.success("Stats fetched successfully", stats));
    }

    @GetMapping("/top")
    public ResponseEntity<ApiResponse<Object>> getTopMarkets() {
        Object markets = marketService.getTopMarkets();
        return ResponseEntity.ok(
                ApiResponse.success("Markets fetched successfully", markets));
    }

    @GetMapping("/klines/{symbol}")
    public ResponseEntity<ApiResponse<Object>> getKlines(
            @PathVariable String symbol,
            @RequestParam(defaultValue = "1h") String interval) {
        Object klines = marketService.getKlines(symbol, interval);
        return ResponseEntity.ok(
                ApiResponse.success("Klines fetched successfully", klines));
    }
}