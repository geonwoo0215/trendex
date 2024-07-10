package com.trendex.trendex.domain.macd.upbitmacd.repository;

import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UpbitMacdRepository extends JpaRepository<UpbitMacd, Long> {

    @Query("SELECT um " +
            "FROM UpbitMacd um " +
            "WHERE um.symbol = :symbol " +
            "AND um.timestamp >= :start  " +
            "ORDER BY um.timestamp ASC")
    List<UpbitMacd> findMarketBySymbolAndTime(@Param("symbol") String symbol, @Param("start") long start);


}
