package com.com.trading.PrachinX.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import java.util.HashMap;

@Service
public class MarketService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BINANCE_API =
            "https://api.binance.com/api/v3";

    public Map<String, Object> getTickerPrice(String symbol) {
        String url = BINANCE_API + "/ticker/price?symbol=" + symbol;
        return restTemplate.getForObject(url, Map.class);
    }

    public Map<String, Object> get24hrStats(String symbol) {
        String url = BINANCE_API + "/ticker/24hr?symbol=" + symbol;
        return restTemplate.getForObject(url, Map.class);
    }

    public Object getTopMarkets() {
        String url = BINANCE_API + "/ticker/24hr";
        return restTemplate.getForObject(url, Object.class);
    }

    public Object getKlines(String symbol, String interval) {
        String url = BINANCE_API + "/klines?symbol="
                + symbol + "&interval=" + interval + "&limit=100";
        return restTemplate.getForObject(url, Object.class);
    }
}