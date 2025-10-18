package com.codedstreams.tradeproducer.mapper;

import com.codedstreams.cryptorisk.contracts.schemas.CryptoTrade;
import com.codedstreams.cryptorisk.contracts.schemas.OrderType;
import com.codedstreams.cryptorisk.contracts.schemas.RiskLevel;
import com.codedstreams.cryptorisk.contracts.schemas.TradeSide;
import com.codedstreams.tradeproducer.entity.TradeEntity;
import com.codedstreams.tradeproducer.model.ProduceTradeRequest;

public class TradeMapper {

    public static CryptoTrade toAvroTrade(String tradeId, ProduceTradeRequest request,
                                          long timestamp, double notional) {
        String[] symbolParts = request.getSymbol().split("/");
        String baseAsset = symbolParts.length > 0 ? symbolParts[0] : "UNKNOWN";
        String quoteAsset = symbolParts.length > 1 ? symbolParts[1] : "USDT";

        return CryptoTrade.newBuilder()
                .setTradeId(tradeId)
                .setUserId(request.getUserId())
                .setCustomerId(request.getCustomerId())
                .setSymbol(request.getSymbol())
                .setBaseAsset(baseAsset)
                .setQuoteAsset(quoteAsset)
                .setSide(TradeSide.valueOf(request.getSide()))
                .setOrderType(OrderType.valueOf(request.getOrderType()))
                .setQuantity(request.getQuantity())
                .setPrice(request.getPrice())
                .setNotional(notional)
                .setTimestamp(timestamp)
                .setExchange(request.getExchange())
                .setWalletAddress(request.getWalletAddress())
                .setRiskLevel(RiskLevel.LOW)
                .build();
    }

    public static TradeEntity toTradeEntity(CryptoTrade trade) {
        TradeEntity entity = new TradeEntity();
        entity.setTradeId(trade.getTradeId().toString());
        entity.setUserId(trade.getUserId().toString());
        entity.setCustomerId(trade.getCustomerId().toString());
        entity.setSymbol(trade.getSymbol().toString());
        entity.setBaseAsset(trade.getBaseAsset().toString());
        entity.setQuoteAsset(trade.getQuoteAsset().toString());
        entity.setSide(trade.getSide().toString());
        entity.setOrderType(trade.getOrderType().toString());
        entity.setQuantity(trade.getQuantity());
        entity.setPrice(trade.getPrice());
        entity.setNotional(trade.getNotional());
        entity.setTimestamp(trade.getTimestamp());
        entity.setExchange(trade.getExchange().toString());
        entity.setWalletAddress(trade.getWalletAddress() != null ? trade.getWalletAddress().toString() : null);
        entity.setRiskLevel(trade.getRiskLevel().toString());

        return entity;
    }

    public static TradeEntity toTradeEntity(ProduceTradeRequest request, String tradeId,
                                            long timestamp, double notional) {
        String[] symbolParts = request.getSymbol().split("/");
        String baseAsset = symbolParts.length > 0 ? symbolParts[0] : "UNKNOWN";
        String quoteAsset = symbolParts.length > 1 ? symbolParts[1] : "USDT";

        TradeEntity entity = new TradeEntity();
        entity.setTradeId(tradeId);
        entity.setUserId(request.getUserId());
        entity.setCustomerId(request.getCustomerId());
        entity.setSymbol(request.getSymbol());
        entity.setBaseAsset(baseAsset);
        entity.setQuoteAsset(quoteAsset);
        entity.setSide(request.getSide());
        entity.setOrderType(request.getOrderType());
        entity.setQuantity(request.getQuantity());
        entity.setPrice(request.getPrice());
        entity.setNotional(notional);
        entity.setTimestamp(timestamp);
        entity.setExchange(request.getExchange());
        entity.setWalletAddress(request.getWalletAddress());
        entity.setRiskLevel("LOW");

        return entity;
    }
}
