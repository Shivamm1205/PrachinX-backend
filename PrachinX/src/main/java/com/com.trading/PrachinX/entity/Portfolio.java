package com.com.trading.PrachinX.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "portfolios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, precision = 20, scale = 8)
    @Builder.Default
    private BigDecimal totalBalance = BigDecimal.ZERO;

    @Column(nullable = false, precision = 20, scale = 8)
    @Builder.Default
    private BigDecimal availableBalance = BigDecimal.ZERO;

    @Column(nullable = false, precision = 20, scale = 8)
    @Builder.Default
    private BigDecimal investedAmount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 20, scale = 8)
    @Builder.Default
    private BigDecimal totalProfitLoss = BigDecimal.ZERO;

    @Column(precision = 10, scale = 4)
    @Builder.Default
    private BigDecimal profitLossPercentage = BigDecimal.ZERO;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    public void deposit(java.math.BigDecimal amount) {
        this.availableBalance = this.availableBalance.add(amount);
        this.totalBalance = this.totalBalance.add(amount);
        this.updatedAt = java.time.LocalDateTime.now();
    }
}