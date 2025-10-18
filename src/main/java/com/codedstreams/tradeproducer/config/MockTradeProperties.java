package com.codedstreams.tradeproducer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "producer.mock")
public class MockTradeProperties {

    private boolean enabled = true;
    private long rateMs = 500;
    private List<String> users;
    private List<String> symbols;
    private List<String> exchanges;

    // Getters and setters
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public long getRateMs() { return rateMs; }
    public void setRateMs(long rateMs) { this.rateMs = rateMs; }

    public List<String> getUsers() { return users; }
    public void setUsers(List<String> users) { this.users = users; }

    public List<String> getSymbols() { return symbols; }
    public void setSymbols(List<String> symbols) { this.symbols = symbols; }

    public List<String> getExchanges() { return exchanges; }
    public void setExchanges(List<String> exchanges) { this.exchanges = exchanges; }
}

