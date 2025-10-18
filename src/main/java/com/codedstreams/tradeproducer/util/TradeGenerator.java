package com.codedstreams.tradeproducer.util;

import com.codedstreams.cryptorisk.contracts.schemas.CryptoTrade;
import com.codedstreams.cryptorisk.contracts.schemas.OrderType;
import com.codedstreams.cryptorisk.contracts.schemas.RiskLevel;
import com.codedstreams.cryptorisk.contracts.schemas.TradeSide;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class TradeGenerator {
    private final Random random = new Random();

    // Mock price data for different symbols
    private static final double BTC_PRICE = 45000.0;
    private static final double ETH_PRICE = 3000.0;
    private static final double SOL_PRICE = 100.0;
    private static final double ADA_PRICE = 1.0;
    private static final double DOT_PRICE = 25.0;

    public CryptoTrade generateMockTrade(List<String> users, List<String> symbols, List<String> exchanges) {
        String tradeId = UUID.randomUUID().toString();
        String userId = users.get(random.nextInt(users.size()));
        String symbol = symbols.get(random.nextInt(symbols.size()));
        String exchange = exchanges.get(random.nextInt(exchanges.size()));

        String[] symbolParts = symbol.split("/");
        String baseAsset = symbolParts[0];
        String quoteAsset = symbolParts[1];

        TradeSide side = random.nextBoolean() ?
                TradeSide.BUY : TradeSide.SELL;

        OrderType orderType = random.nextDouble() > 0.3 ?
                OrderType.LIMIT : OrderType.MARKET;

        double price = getCurrentPrice(symbol);
        double quantity = generateRealisticQuantity(symbol, price);
        double notional = quantity * price;

        // Add some price variation
        price = price * (0.99 + random.nextDouble() * 0.02);

        return CryptoTrade.newBuilder()
                .setTradeId(tradeId)
                .setUserId(userId)
                .setCustomerId("CUST_" + (1000 + random.nextInt(9000)))
                .setSymbol(symbol)
                .setBaseAsset(baseAsset)
                .setQuoteAsset(quoteAsset)
                .setSide(side)
                .setOrderType(orderType)
                .setQuantity(quantity)
                .setPrice(price)
                .setNotional(notional)
                .setTimestamp(System.currentTimeMillis())
                .setExchange(exchange)
                .setWalletAddress(generateWalletAddress())
                .setRiskLevel(RiskLevel.LOW)
                .setSessionId("SESSION_" + UUID.randomUUID().toString().substring(0, 8))
                .setIpAddress(generateIpAddress())
                .build();
    }

    private double getCurrentPrice(String symbol) {
        return switch (symbol) {
            case "BTC/USDT" -> BTC_PRICE;
            case "ETH/USDT" -> ETH_PRICE;
            case "SOL/USDT" -> SOL_PRICE;
            case "ADA/USDT" -> ADA_PRICE;
            case "DOT/USDT" -> DOT_PRICE;
            default -> 100.0;
        };
    }

    private double generateRealisticQuantity(String symbol, double price) {
        // Generate quantities that make sense for each symbol
        return switch (symbol) {
            case "BTC/USDT" -> 0.001 + random.nextDouble() * 0.1; // 0.001 to 0.1 BTC
            case "ETH/USDT" -> 0.01 + random.nextDouble() * 1.0;   // 0.01 to 1 ETH
            case "SOL/USDT" -> 0.1 + random.nextDouble() * 10.0;   // 0.1 to 10 SOL
            case "ADA/USDT" -> 10 + random.nextDouble() * 1000;    // 10 to 1000 ADA
            case "DOT/USDT" -> 1 + random.nextDouble() * 100;      // 1 to 100 DOT
            default -> 1 + random.nextDouble() * 10;
        };
    }

    private String generateWalletAddress() {
        if (random.nextDouble() > 0.7) { // 30% chance to have wallet address
            return "0x" + UUID.randomUUID().toString().replace("-", "").substring(0, 40);
        }
        return null;
    }

    private String generateIpAddress() {
        return "192.168." + random.nextInt(256) + "." + random.nextInt(256);
    }
}
