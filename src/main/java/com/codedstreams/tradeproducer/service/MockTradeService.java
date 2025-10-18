package com.codedstreams.tradeproducer.service;

import com.codedstreams.cryptorisk.contracts.schemas.CryptoTrade;
import com.codedstreams.tradeproducer.config.MockTradeProperties;
import com.codedstreams.tradeproducer.util.TradeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class MockTradeService {

    private final TradeProducerService tradeProducerService;
    private final TradeGenerator tradeGenerator;
    private final MockTradeProperties properties;

    private volatile boolean isRunning = false;

    public MockTradeService(TradeProducerService tradeProducerService,
                            TradeGenerator tradeGenerator,
                            MockTradeProperties properties) {
        this.tradeProducerService = tradeProducerService;
        this.tradeGenerator = tradeGenerator;
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        if (properties.isEnabled()) {
            System.out.println("Mock trade service initialized with rate: " + properties.getRateMs());
            System.out.println("Mock users: " + properties.getUsers());
            System.out.println("Mock symbols: " + properties.getSymbols());
            System.out.println("Mock exchanges: " + properties.getExchanges());
        }
    }

    @Scheduled(fixedRateString = "#{${producer.mock.rate-ms:500}}")
    public void generateMockTrade() {
        if (!properties.isEnabled() || !isRunning) return;

        try {
            CryptoTrade mockTrade = tradeGenerator.generateMockTrade(
                    properties.getUsers(),
                    properties.getSymbols(),
                    properties.getExchanges()
            );
            tradeProducerService.produceTrade(mockTrade);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startMockTrading() { isRunning = true; }
    public void stopMockTrading() { isRunning = false; }
    public boolean isMockTradingRunning() { return isRunning; }
}
