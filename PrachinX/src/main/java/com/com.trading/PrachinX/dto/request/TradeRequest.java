package com.com.trading.PrachinX.dto.request;

import com.com.trading.PrachinX.entity.Trade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TradeRequest {

    @NotBlank(message = "Symbol is required")
    private String symbol;

    @NotNull(message = "Trade type is required")
    private Trade.TradeType type;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private BigDecimal quantity;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    private String notes;
}