package com.com.trading.PrachinX.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "portfolios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

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

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}