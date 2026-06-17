package com.com.trading.PrachinX.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String symbol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeType type;

    @Column(nullable = false, precision = 20, scale = 8)
    private BigDecimal quantity;

    @Column(nullable = false, precision = 20, scale = 8)
    private BigDecimal price;

    @Column(precision = 20, scale = 8)
    private BigDecimal totalValue;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TradeStatus status = TradeStatus.PENDING;

    private String notes;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime executedAt;

    public enum TradeType {
        BUY, SELL
    }

    public enum TradeStatus {
        PENDING, EXECUTED, CANCELLED, FAILED
    }
}