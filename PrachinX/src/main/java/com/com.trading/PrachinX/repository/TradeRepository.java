package com.com.trading.PrachinX.repository;

import com.com.trading.PrachinX.entity.Trade;
import com.com.trading.PrachinX.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, String> {
    List<Trade> findByUserOrderByCreatedAtDesc(User user);
    List<Trade> findByUserAndSymbol(User user, String symbol);
    List<Trade> findByUserAndStatus(User user, Trade.TradeStatus status);
}