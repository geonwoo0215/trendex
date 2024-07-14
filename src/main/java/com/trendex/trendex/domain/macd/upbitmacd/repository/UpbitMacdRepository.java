package com.trendex.trendex.domain.macd.upbitmacd.repository;

import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UpbitMacdRepository extends JpaRepository<UpbitMacd, Long> {

    @Query("SELECT um " +
            "FROM UpbitMacd um " +
            "WHERE um.market = :market " +
            "AND um.timestamp >= :start  " +
            "ORDER BY um.timestamp ASC")
    List<UpbitMacd> findMarketBySymbolAndTime(@Param("market") String market, @Param("start") long start);


    @Query("SELECT um " +
            "FROM UpbitMacd um " +
            "WHERE um.market = :market " +
            "ORDER BY um.timestamp DESC")
    Optional<UpbitMacd> findLatest(@Param("market") String market);

    @Query("SELECT um " +
            "FROM UpbitMacd um " +
            "WHERE um.market " +
            "IN :markets " +
            "AND um.timestamp = (" +
            "SELECT MAX(u.timestamp) " +
            "FROM UpbitMacd u " +
            "WHERE u.market = um.market)")
    List<UpbitMacd> findLatestForMarkets(@Param("markets") List<String> markets);

}
