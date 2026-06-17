package com.com.trading.PrachinX.repository;

import com.com.trading.PrachinX.entity.TradingBot;
import com.com.trading.PrachinX.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BotRepository extends JpaRepository<TradingBot, Long> {

    List<TradingBot> findByUser(User user);

    List<TradingBot> findByUserAndStatus(User user, TradingBot.BotStatus status);
}
