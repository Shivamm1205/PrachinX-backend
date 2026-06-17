package com.com.trading.PrachinX.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AIController {

    @GetMapping("/predict/price")
    public ResponseEntity<Map<String, Object>> predictPrice(
            @RequestParam String symbol) {
        Map<String, Object> data = new HashMap<>();
        data.put("symbol", symbol);
        data.put("currentPrice", getRandomPrice(symbol));
        data.put("predictedPrice", getRandomPrice(symbol) * 1.05);
        data.put("confidence", "87%");
        data.put("trend", "BULLISH");
        data.put("recommendation", "BUY");
        data.put("timeframe", "24h");

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Prediction generated");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/predict/sentiment")
    public ResponseEntity<Map<String, Object>> sentiment(
            @RequestParam String symbol) {
        Map<String, Object> data = new HashMap<>();
        data.put("symbol", symbol);
        data.put("sentiment", "POSITIVE");
        data.put("score", 0.78);
        data.put("fearGreedIndex", 65);
        data.put("socialMentions", 12500);
        data.put("newsScore", "BULLISH");

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Sentiment analyzed");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }































    @GetMapping("/strategy")
    public ResponseEntity<Map<String, Object>> strategy(
            @RequestParam String symbol,
            @RequestParam(defaultValue = "medium") String risk) {
        Map<String, Object> data = new HashMap<>();
        data.put("symbol", symbol);
        data.put("risk", risk);
        data.put("strategy", "MOMENTUM");
        data.put("entryPoint", getRandomPrice(symbol) * 0.98);
        data.put("targetPrice", getRandomPrice(symbol) * 1.08);
        data.put("stopLoss", getRandomPrice(symbol) * 0.95);
        data.put("expectedReturn", "8%");
        data.put("timeframe", "1-3 days");

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Strategy generated");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, Object>> chat(
            @RequestBody Map<String, String> request) {
        String message = request.get("message");
        String reply = generateReply(message);

        Map<String, Object> data = new HashMap<>();
        data.put("reply", reply);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    private double getRandomPrice(String symbol) {
        Map<String, Double> prices = new HashMap<>();
        prices.put("BTCUSDT", 67000.0);
        prices.put("ETHUSDT", 3500.0);
        prices.put("BNBUSDT", 580.0);
        prices.put("SOLUSDT", 170.0);
        prices.put("ADAUSDT", 0.45);
        return prices.getOrDefault(symbol, 100.0);
    }

    private String generateReply(String message) {
        String msg = message.toLowerCase();
        if (msg.contains("btc") || msg.contains("bitcoin"))
            return "Bitcoin is showing strong momentum! Current trend is bullish with key resistance at $68,000. Consider DCA strategy for long-term holding.";
        if (msg.contains("eth") || msg.contains("ethereum"))
            return "Ethereum is consolidating above support. With upcoming upgrades, ETH looks promising for medium-term investment.";
        if (msg.contains("buy"))
            return "Based on current market analysis, focus on strong fundamentals. Consider BTC, ETH for stable returns. Always use stop-loss orders!";
        if (msg.contains("sell"))
            return "Market sentiment is mixed. If you're in profit, consider taking partial profits. Keep 40% for potential further upside.";
        if (msg.contains("market"))
            return "Current market shows moderate volatility. Fear & Greed index at 65 (Greed). Top performers today: BTC +2.3%, ETH +1.8%, SOL +4.2%.";
        return "Great question! Based on current market analysis, I recommend diversifying your portfolio across BTC, ETH, and SOL. Always do your own research and never invest more than you can afford to lose!";
    }
}
