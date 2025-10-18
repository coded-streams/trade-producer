package com.codedstreams.tradeproducer.service;

import com.codedstreams.tradeproducer.model.ProducerStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class MetricsService {

    private final ProducerStats stats = new ProducerStats();
    private final AtomicLong totalTrades = new AtomicLong(0);
    private final AtomicLong successfulProductions = new AtomicLong(0);
    private final AtomicLong failedProductions = new AtomicLong(0);

    public void recordSuccessfulProduction() {
        totalTrades.incrementAndGet();
        successfulProductions.incrementAndGet();
        stats.setLastProducedTimestamp(LocalDateTime.now());
        updateStats();
    }

    public void recordFailedProduction() {
        totalTrades.incrementAndGet();
        failedProductions.incrementAndGet();
        updateStats();
    }

    private void updateStats() {
        stats.setTotalTradesProduced(totalTrades.get());
        stats.setSuccessfulProductions(successfulProductions.get());
        stats.setFailedProductions(failedProductions.get());
        stats.setUptimeMinutes(ChronoUnit.MINUTES.between(stats.getStartTime(), LocalDateTime.now()));
    }

    public ProducerStats getStats() {
        updateStats();
        return stats;
    }

    public long getTotalTradesProduced() {
        return totalTrades.get();
    }

    public long getSuccessfulProductions() {
        return successfulProductions.get();
    }

    public long getFailedProductions() {
        return failedProductions.get();
    }

    public double getSuccessRate() {
        long total = totalTrades.get();
        return total > 0 ? (double) successfulProductions.get() / total * 100 : 0.0;
    }
}
