package com.codedstreams.tradeproducer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class ProducerStats {

    @JsonProperty("totalTradesProduced")
    private long totalTradesProduced;

    @JsonProperty("successfulProductions")
    private long successfulProductions;

    @JsonProperty("failedProductions")
    private long failedProductions;

    @JsonProperty("lastProducedTimestamp")
    private LocalDateTime lastProducedTimestamp;

    @JsonProperty("startTime")
    private LocalDateTime startTime;

    @JsonProperty("uptimeMinutes")
    private long uptimeMinutes;

    // Constructors
    public ProducerStats() {
        this.startTime = LocalDateTime.now();
    }

    // Getters and Setters
    public long getTotalTradesProduced() { return totalTradesProduced; }
    public void setTotalTradesProduced(long totalTradesProduced) { this.totalTradesProduced = totalTradesProduced; }

    public long getSuccessfulProductions() { return successfulProductions; }
    public void setSuccessfulProductions(long successfulProductions) { this.successfulProductions = successfulProductions; }

    public long getFailedProductions() { return failedProductions; }
    public void setFailedProductions(long failedProductions) { this.failedProductions = failedProductions; }

    public LocalDateTime getLastProducedTimestamp() { return lastProducedTimestamp; }
    public void setLastProducedTimestamp(LocalDateTime lastProducedTimestamp) { this.lastProducedTimestamp = lastProducedTimestamp; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public long getUptimeMinutes() { return uptimeMinutes; }
    public void setUptimeMinutes(long uptimeMinutes) { this.uptimeMinutes = uptimeMinutes; }

    // Utility methods
    public void incrementTotalTrades() { this.totalTradesProduced++; }
    public void incrementSuccess() { this.successfulProductions++; }
    public void incrementFailures() { this.failedProductions++; }
    public void updateLastProduced() { this.lastProducedTimestamp = LocalDateTime.now(); }

    public double getSuccessRate() {
        return totalTradesProduced > 0 ? (double) successfulProductions / totalTradesProduced * 100 : 0.0;
    }
}
