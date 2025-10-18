package com.codedstreams.tradeproducer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "trades", indexes = {
        @Index(name = "idx_user_id", columnList = "userId"),
        @Index(name = "idx_timestamp", columnList = "timestamp"),
        @Index(name = "idx_symbol", columnList = "symbol")
})
@NoArgsConstructor
@AllArgsConstructor
public class TradeEntity {
    @Id
    private String tradeId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String symbol;

    private String baseAsset;
    private String quoteAsset;

    @Column(nullable = false)
    private String side; // BUY, SELL

    private String orderType; // MARKET, LIMIT, STOP_LOSS

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double notional;

    @Column(nullable = false)
    private Long timestamp;

    private String exchange;
    private String walletAddress;
    private String riskLevel;
    private Boolean flagged = false;
    private String riskReason;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
