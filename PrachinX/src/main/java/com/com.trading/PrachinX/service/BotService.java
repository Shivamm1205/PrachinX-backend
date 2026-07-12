package com.com.trading.PrachinX.service;

import com.com.trading.PrachinX.entity.TradingBot;
import com.com.trading.PrachinX.entity.User;
import com.com.trading.PrachinX.exception.CustomException;
import com.com.trading.PrachinX.repository.BotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BotService {

    @Autowired
    private BotRepository botRepository;

    @Autowired
    private UserService userService;

    public TradingBot createBot(TradingBot bot, String email) {
        User user = userService.getUserByEmail(email);
        bot.setUser(user);
        bot.setStatus(TradingBot.BotStatus.INACTIVE);
        return botRepository.save(bot);
    }

    public List<TradingBot> getUserBots(String email) {
        User user = userService.getUserByEmail(email);
        return botRepository.findByUser(user);
    }

    public TradingBot getBotById(String id) {
        return botRepository.findById(id)
                .orElseThrow(() ->
                        new CustomException("Bot not found", HttpStatus.NOT_FOUND));
    }

    public TradingBot startBot(String id, String email) {
        TradingBot bot = getBotById(id);
        if (!bot.getUser().getEmail().equals(email)) {
            throw new CustomException("Unauthorized", HttpStatus.FORBIDDEN);
        }
        bot.setStatus(TradingBot.BotStatus.ACTIVE);
        return botRepository.save(bot);
    }

    public TradingBot stopBot(String id, String email) {
        TradingBot bot = getBotById(id);
        if (!bot.getUser().getEmail().equals(email)) {
            throw new CustomException("Unauthorized", HttpStatus.FORBIDDEN);
        }
        bot.setStatus(TradingBot.BotStatus.INACTIVE);
        return botRepository.save(bot);
    }

    public void deleteBot(String id, String email) {
        TradingBot bot = getBotById(id);
        if (!bot.getUser().getEmail().equals(email)) {
            throw new CustomException("Unauthorized", HttpStatus.FORBIDDEN);
        }
        botRepository.delete(bot);
    }
}