package com.codedstreams.tradeproducer.repository;

import com.codedstreams.tradeproducer.entity.TradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<TradeEntity, String> {

    List<TradeEntity> findByUserId(String userId);

    List<TradeEntity> findBySymbol(String symbol);

    List<TradeEntity> findByTimestampBetween(Long startTime, Long endTime);

    @Query("SELECT COUNT(t) FROM TradeEntity t WHERE t.createdAt >= :since")
    Long countTradesSince(@Param("since") LocalDateTime since);

    @Query("SELECT t FROM TradeEntity t WHERE t.flagged = true")
    List<TradeEntity> findFlaggedTrades();

    @Query("SELECT t.symbol, COUNT(t) FROM TradeEntity t GROUP BY t.symbol")
    List<Object[]> countTradesBySymbol();
}
