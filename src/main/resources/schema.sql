CREATE INDEX IF NOT EXISTS idx_trade_user_timestamp ON trades(userId, timestamp);
CREATE INDEX IF NOT EXISTS idx_trade_symbol_timestamp ON trades(symbol, timestamp);
CREATE INDEX IF NOT EXISTS idx_trade_created_at ON trades(createdAt);

-- View for trade statistics
CREATE VIEW trade_stats AS
SELECT
    symbol,
    COUNT(*) as trade_count,
    AVG(quantity) as avg_quantity,
    AVG(price) as avg_price,
    AVG(notional) as avg_notional,
    SUM(notional) as total_notional,
    MAX(timestamp) as last_trade_time
FROM trades
GROUP BY symbol;