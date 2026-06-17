package com.com.trading.PrachinX.service;

import com.com.trading.PrachinX.entity.Portfolio;
import com.com.trading.PrachinX.entity.Trade;
import com.com.trading.PrachinX.entity.User;
import com.com.trading.PrachinX.exception.CustomException;
import com.com.trading.PrachinX.repository.PortfolioRepository;
import com.com.trading.PrachinX.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    public Portfolio getPortfolio(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(
                        "User not found", HttpStatus.NOT_FOUND));
        return portfolioRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(
                        "Portfolio not found", HttpStatus.NOT_FOUND));
    }

    public Portfolio getOrCreatePortfolio(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(
                        "User not found", HttpStatus.NOT_FOUND));
        return portfolioRepository.findByUser(user)
                .orElseGet(() -> createPortfolio(user));
    }

    public Portfolio createPortfolio(User user) {
        Portfolio portfolio = Portfolio.builder()
                .user(user)
                .totalBalance(BigDecimal.ZERO)
                .availableBalance(BigDecimal.ZERO)
                .investedAmount(BigDecimal.ZERO)
                .totalProfitLoss(BigDecimal.ZERO)
                .build();
        return portfolioRepository.save(portfolio);
    }

    public Portfolio deposit(String email, BigDecimal amount) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(
                        "User not found", HttpStatus.NOT_FOUND));
        Portfolio portfolio = portfolioRepository
                .findByUser(user)
                .orElseGet(() -> createPortfolio(user));
        portfolio.setAvailableBalance(
                portfolio.getAvailableBalance().add(amount));
        portfolio.setTotalBalance(
                portfolio.getTotalBalance().add(amount));
        portfolio.setUpdatedAt(LocalDateTime.now());
        return portfolioRepository.save(portfolio);
    }

    public void updatePortfolioAfterTrade(User user, Trade trade) {
        Portfolio portfolio = portfolioRepository
                .findByUser(user)
                .orElseGet(() -> createPortfolio(user));

        if (trade.getType() == Trade.TradeType.BUY) {
            portfolio.setInvestedAmount(
                    portfolio.getInvestedAmount()
                            .add(trade.getTotalValue()));
            portfolio.setAvailableBalance(
                    portfolio.getAvailableBalance()
                            .subtract(trade.getTotalValue()));
        } else {
            portfolio.setAvailableBalance(
                    portfolio.getAvailableBalance()
                            .add(trade.getTotalValue()));
            portfolio.setInvestedAmount(
                    portfolio.getInvestedAmount()
                            .subtract(trade.getTotalValue()));
        }

        portfolio.setTotalBalance(
                portfolio.getAvailableBalance()
                        .add(portfolio.getInvestedAmount()));
        portfolio.setUpdatedAt(LocalDateTime.now());
        portfolioRepository.save(portfolio);
    }
}