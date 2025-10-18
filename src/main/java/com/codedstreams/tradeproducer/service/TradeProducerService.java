package com.codedstreams.tradeproducer.service;

import com.codedstreams.cryptorisk.contracts.schemas.CryptoTrade;
import com.codedstreams.tradeproducer.entity.TradeEntity;
import com.codedstreams.tradeproducer.mapper.TradeMapper;
import com.codedstreams.tradeproducer.model.ProduceTradeRequest;
import com.codedstreams.tradeproducer.repository.TradeRepository;
import com.codedstreams.tradeproducer.util.AvroSerialization;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@RequiredArgsConstructor
public class TradeProducerService {
    private static final Logger log = LoggerFactory.getLogger(TradeProducerService.class);

    private final KafkaTemplate<String, CryptoTrade> kafkaTemplate;
    private final TradeRepository tradeRepository;
    private final TradeValidationService validationService;
    private final MetricsService metricsService;


    public void produceTrade(ProduceTradeRequest request) {
        try {
            // Validate the trade request
            validationService.validateTradeRequest(request);

            // Generate trade ID if not provided
            String tradeId = UUID.randomUUID().toString();
            long timestamp = System.currentTimeMillis();

            // Calculate notional value
            double notional = request.getQuantity() * request.getPrice();

            // Create Avro trade object
            CryptoTrade avroTrade = TradeMapper.toAvroTrade(tradeId, request, timestamp, notional);

            // Serialize to Avro
            //Object serializedTrade = AvroSerialization.serializeTrade(avroTrade);
            // Send to Kafka
            CompletableFuture<SendResult<String, CryptoTrade>> future =
                    kafkaTemplate.send("crypto-trades", request.getUserId(), avroTrade);

            // Handle async result
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    // Success - save to database
                    TradeEntity entity = TradeMapper.toTradeEntity(avroTrade);
                    tradeRepository.save(entity);

                    metricsService.recordSuccessfulProduction();
                    log.info("Successfully produced trade: {} for user: {}", tradeId, request.getUserId());
                } else {
                    // Failure
                    metricsService.recordFailedProduction();
                    log.error("Failed to produce trade: {} for user: {}", tradeId, request.getUserId(), ex);
                }
            });

        } catch (Exception e) {
            metricsService.recordFailedProduction();
            log.error("Error producing trade for user: {}", request.getUserId(), e);
            throw new RuntimeException("Failed to produce trade: " + e.getMessage(), e);
        }
    }

    public void produceTrade(CryptoTrade trade) {
        try {
            // Serialize to Avro
            Object serializedTrade = AvroSerialization.serializeTrade(trade);

            // Send to Kafka
            CompletableFuture<SendResult<String, CryptoTrade>> future =
                    kafkaTemplate.send("crypto-trades", trade.getUserId().toString(), trade);

            // Handle async result
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    // Success - save to database
                    TradeEntity entity = TradeMapper.toTradeEntity(trade);
                    tradeRepository.save(entity);

                    metricsService.recordSuccessfulProduction();
                    log.debug("Successfully produced trade: {}", trade.getTradeId());
                } else {
                    // Failure
                    metricsService.recordFailedProduction();
                    log.error("Failed to produce trade: {}", trade.getTradeId(), ex);
                }
            });

        } catch (Exception e) {
            metricsService.recordFailedProduction();
            log.error("Error producing trade: {}", trade.getTradeId(), e);
            throw new RuntimeException("Failed to produce trade: " + e.getMessage(), e);
        }
    }
}
