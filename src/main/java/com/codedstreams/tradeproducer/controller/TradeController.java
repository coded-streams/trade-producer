package com.codedstreams.tradeproducer.controller;

import com.codedstreams.tradeproducer.model.ProduceTradeRequest;
import com.codedstreams.tradeproducer.service.TradeProducerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/trades")
public class TradeController {
    private static final Logger log = LoggerFactory.getLogger(TradeController.class);

    private final TradeProducerService tradeProducerService;

    public TradeController(TradeProducerService tradeProducerService) {
        this.tradeProducerService = tradeProducerService;
    }

    @PostMapping("/produce")
    public ResponseEntity<String> produceTrade(@Valid @RequestBody ProduceTradeRequest request) {
        try {
            tradeProducerService.produceTrade(request);
            return ResponseEntity.ok("Trade produced successfully");
        } catch (Exception e) {
            log.error("Failed to produce trade for user: {}", request.getUserId(), e);
            return ResponseEntity.badRequest().body("Failed to produce trade: " + e.getMessage());
        }
    }

    @PostMapping("/produce-batch")
    public ResponseEntity<String> produceTradeBatch(@Valid @RequestBody List<ProduceTradeRequest> requests) {
        try {
            for (ProduceTradeRequest request : requests) {
                tradeProducerService.produceTrade(request);
            }
            return ResponseEntity.ok("Batch of " + requests.size() + " trades produced successfully");
        } catch (Exception e) {
            log.error("Failed to produce trade batch", e);
            return ResponseEntity.badRequest().body("Failed to produce trade batch: " + e.getMessage());
        }
    }
}
