package com.codedstreams.tradeproducer.service;

import com.codedstreams.tradeproducer.model.ProduceTradeRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.Set;
import java.util.HashSet;

@Service
@Validated
public class TradeValidationService {

    private static final Set<String> VALID_SYMBOLS = Set.of(
            "BTC/USDT", "ETH/USDT", "SOL/USDT", "ADA/USDT", "DOT/USDT",
            "BNB/USDT", "XRP/USDT", "DOGE/USDT", "MATIC/USDT", "LTC/USDT"
    );

    private static final Set<String> VALID_ORDER_TYPES = Set.of(
            "MARKET", "LIMIT", "STOP_LOSS", "TAKE_PROFIT"
    );

    private static final Set<String> VALID_EXCHANGES = Set.of(
            "BINANCE", "COINBASE", "KRAKEN", "FTX", "KUCOIN",
            "GEMINI", "BITSTAMP", "HUOBI", "OKX"
    );

    public void validateTradeRequest(@Valid ProduceTradeRequest request) {
        // Validate symbol
        if (!VALID_SYMBOLS.contains(request.getSymbol())) {
            throw new IllegalArgumentException("Invalid symbol: " + request.getSymbol());
        }

        // Validate order type
        if (request.getOrderType() != null && !VALID_ORDER_TYPES.contains(request.getOrderType())) {
            throw new IllegalArgumentException("Invalid order type: " + request.getOrderType());
        }

        // Validate exchange
        if (request.getExchange() != null && !VALID_EXCHANGES.contains(request.getExchange())) {
            throw new IllegalArgumentException("Invalid exchange: " + request.getExchange());
        }

        // Validate wallet address format if provided
        if (request.getWalletAddress() != null && !request.getWalletAddress().isEmpty()) {
            validateWalletAddress(request.getWalletAddress());
        }
    }

    private void validateWalletAddress(String walletAddress) {
        // Basic wallet address validation
        if (walletAddress.startsWith("0x")) {
            // Ethereum-style address
            if (walletAddress.length() != 42) {
                throw new IllegalArgumentException("Invalid Ethereum wallet address length");
            }
            if (!walletAddress.matches("^0x[a-fA-F0-9]{40}$")) {
                throw new IllegalArgumentException("Invalid Ethereum wallet address format");
            }
        } else if (walletAddress.startsWith("bc1") || walletAddress.startsWith("1") || walletAddress.startsWith("3")) {
            // Bitcoin-style address - basic validation
            if (walletAddress.length() < 26 || walletAddress.length() > 62) {
                throw new IllegalArgumentException("Invalid Bitcoin wallet address length");
            }
        }
    }
}
