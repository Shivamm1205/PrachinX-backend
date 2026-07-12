package com.com.trading.PrachinX.service;

import com.com.trading.PrachinX.dto.request.TradeRequest;
import com.com.trading.PrachinX.entity.Portfolio;
import com.com.trading.PrachinX.entity.Trade;
import com.com.trading.PrachinX.entity.User;
import com.com.trading.PrachinX.exception.CustomException;
import com.com.trading.PrachinX.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TradingService {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PortfolioService portfolioService;

    public Trade executeTrade(TradeRequest request, String email) {
        User user = userService.getUserByEmail(email);

        BigDecimal totalValue = request.getPrice()
                .multiply(request.getQuantity());

        // Check balance for BUY orders
        if (request.getType() == Trade.TradeType.BUY) {
            Portfolio portfolio = portfolioService
                    .getOrCreatePortfolio(email);
            if (portfolio.getAvailableBalance()
                    .compareTo(totalValue) < 0) {
                throw new CustomException(
                        "Insufficient balance! Available: $"
                                + portfolio.getAvailableBalance()
                                + " Required: $" + totalValue,
                        HttpStatus.BAD_REQUEST);
            }
        }

        Trade trade = Trade.builder()
                .user(user)
                .symbol(request.getSymbol())
                .type(request.getType())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .totalValue(totalValue)
                .status(Trade.TradeStatus.EXECUTED)
                .notes(request.getNotes())
                .executedAt(LocalDateTime.now())
                .build();

        Trade savedTrade = tradeRepository.save(trade);
        portfolioService.updatePortfolioAfterTrade(user, trade);
        return savedTrade;
    }

    public List<Trade> getUserTrades(String email) {
        User user = userService.getUserByEmail(email);
        return tradeRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Trade getTradeById(String id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Trade not found", HttpStatus.NOT_FOUND));
    }

    public Trade cancelTrade(String id, String email) {{
        Trade trade = getTradeById(id);
        if (!trade.getUser().getEmail().equals(email)) {
            throw new CustomException(
                    "Unauthorized", HttpStatus.FORBIDDEN);
        }
        if (trade.getStatus() != Trade.TradeStatus.PENDING) {
            throw new CustomException(
                    "Only pending trades can be cancelled",
                    HttpStatus.BAD_REQUEST);
        }
        trade.setStatus(Trade.TradeStatus.CANCELLED);
        return tradeRepository.save(trade);
    }
} }