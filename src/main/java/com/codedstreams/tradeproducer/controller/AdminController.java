package com.codedstreams.tradeproducer.controller;

import com.codedstreams.tradeproducer.config.MockTradeProperties;
import com.codedstreams.tradeproducer.model.ProducerStats;
import com.codedstreams.tradeproducer.repository.TradeRepository;
import com.codedstreams.tradeproducer.service.MetricsService;
import com.codedstreams.tradeproducer.service.MockTradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private final MetricsService metricsService;
    private final MockTradeService mockTradeService;
    private final MockTradeProperties mockTradeProperties;
    private final TradeRepository tradeRepository;

    public AdminController(MetricsService metricsService,
                           MockTradeService mockTradeService,
                           MockTradeProperties mockTradeProperties,
                           TradeRepository tradeRepository) {
        this.metricsService = metricsService;
        this.mockTradeService = mockTradeService;
        this.mockTradeProperties = mockTradeProperties;
        this.tradeRepository = tradeRepository;
    }

    @GetMapping("/stats")
    public ResponseEntity<ProducerStats> getProducerStats() {
        return ResponseEntity.ok(metricsService.getStats());
    }

    @PostMapping("/mock-trading/start")
    public ResponseEntity<String> startMockTrading() {
        if (!mockTradeProperties.isEnabled()) {
            return ResponseEntity.badRequest().body("Mock trading is disabled");
        }

        mockTradeService.startMockTrading();
        return ResponseEntity.ok("Mock trading started");
    }

    @PostMapping("/mock-trading/stop")
    public ResponseEntity<String> stopMockTrading() {
        mockTradeService.stopMockTrading();
        return ResponseEntity.ok("Mock trading stopped");
    }

    @GetMapping("/mock-trading/status")
    public ResponseEntity<Map<String, Object>> getMockTradingStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("enabled", mockTradeProperties.isEnabled());
        status.put("running", mockTradeService.isMockTradingRunning());
        status.put("rateMs", mockTradeProperties.getRateMs());
        status.put("users", mockTradeProperties.getUsers());
        status.put("symbols", mockTradeProperties.getSymbols());
        status.put("exchanges", mockTradeProperties.getExchanges());

        return ResponseEntity.ok(status);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "trade-producer");
        health.put("timestamp", Instant.now().toString());
        return ResponseEntity.ok(health);
    }
}
