package com.com.trading.PrachinX.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import java.util.*;

@Service
public class AIService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${ai.engine.url:http://localhost:8000}")
    private String aiEngineUrl;

    public Map<String, Object> getPricePrediction(String symbol) {
        String url = aiEngineUrl + "/api/predict/price?symbol=" + symbol;
        try {
            return restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "AI Engine unavailable");
            error.put("symbol", symbol);
            return error;
        }
    }

    public Map<String, Object> getSentimentAnalysis(String symbol) {
        String url = aiEngineUrl + "/api/predict/sentiment?symbol=" + symbol;
        try {
            return restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "AI Engine unavailable");
            error.put("symbol", symbol);
            return error;
        }
    }

    public Map<String, Object> getTradingStrategy(String symbol, String risk) {
        String url = aiEngineUrl + "/api/strategy?symbol="
                + symbol + "&risk=" + risk;
        try {
            return restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "AI Engine unavailable");
            return error;
        }
    }

    public Map<String, Object> chatWithAI(String message) {
        String url = aiEngineUrl + "/api/chat";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> body = new HashMap<>();
        body.put("message", message);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    url, entity, Map.class);
            return response.getBody();
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "AI Engine unavailable");
            return error;
        }
    }
}