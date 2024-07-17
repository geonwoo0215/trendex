package com.trendex.trendex.domain.macd.upbitmacd.repository;

import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UpbitMacdRepository extends JpaRepository<UpbitMacd, Long> {

    @Query("SELECT um " +
            "FROM UpbitMacd um " +
            "WHERE um.market = :market " +
            "AND um.timestamp >= :start  " +
            "ORDER BY um.timestamp ASC")
    List<UpbitMacd> findMacdByMarketAndTime(@Param("market") String market, @Param("start") long start);

//    @Query("SELECT um " +
//            "FROM UpbitMacd um " +
//            "WHERE um.market " +
//            "IN :markets " +
//            "AND um.timestamp = (" +
//            "SELECT MAX(u.timestamp) " +
//            "FROM UpbitMacd u " +
//            "WHERE u.market = um.market)")
//    List<UpbitMacd> findLatestForMarkets(@Param("markets") List<String> markets);

    @Query(value = "SELECT um1.id, um1.market, um1.macd_value, um1.macd_signal_value, um1.signal_higher_than_macd, um1.timestamp " +
            "FROM upbit_macd um1 " +
            "INNER JOIN ( " +
            "    SELECT market, MAX(timestamp) AS max_timestamp " +
            "    FROM upbit_macd um2 " +
            "    WHERE market IN :markets " +
            "    GROUP BY market " +
            ") um2 ON um1.market = um2.market AND um1.timestamp = um2.max_timestamp",
            nativeQuery = true)
    List<UpbitMacd> findLatestForMarkets(@Param("markets") List<String> markets);
}
