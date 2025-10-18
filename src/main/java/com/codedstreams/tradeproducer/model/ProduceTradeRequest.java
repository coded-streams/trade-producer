package com.codedstreams.tradeproducer.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class ProduceTradeRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Customer ID is required")
    private String customerId;

    @NotBlank(message = "Symbol is required")
    private String symbol;

    @NotBlank(message = "Side is required")
    @Pattern(regexp = "BUY|SELL", message = "Side must be BUY or SELL")
    private String side;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Double quantity;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    private String orderType = "LIMIT";
    private String exchange = "BINANCE";
    private String walletAddress;
}
