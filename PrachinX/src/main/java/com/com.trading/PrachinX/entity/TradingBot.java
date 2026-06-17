package com.com.trading.PrachinX.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trading_bots")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradingBot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String strategy;

    @Column(nullable = false)
    private String tradingPair;

    @Column(precision = 20, scale = 8)
    @Builder.Default
    private BigDecimal investmentAmount = BigDecimal.ZERO;

    @Column(precision = 20, scale = 8)
    @Builder.Default
    private BigDecimal profitLoss = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BotStatus status = BotStatus.INACTIVE;

    private String configuration;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum BotStatus {
        ACTIVE, INACTIVE, PAUSED, ERROR
    }
}
