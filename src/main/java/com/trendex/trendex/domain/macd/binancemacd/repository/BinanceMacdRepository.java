package com.trendex.trendex.domain.macd.binancemacd.repository;

import com.trendex.trendex.domain.macd.binancemacd.model.BinanceMacd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BinanceMacdRepository extends JpaRepository<BinanceMacd, Long> {

    @Query("SELECT bm " +
            "FROM BinanceMacd bm " +
            "WHERE bm.symbol = :symbol " +
            "AND bm.timestamp >= :start  " +
            "ORDER BY bm.timestamp ASC")
    List<BinanceMacd> findMacdBySymbolAndTime(@Param("symbol") String symbol, @Param("start") long start);

    @Query(value = "SELECT bm1.id, bm1.symbol, bm1.macd_value, bm1.macd_signal_value, bm1.signal_higher_than_macd, bm1.timestamp " +
            "FROM binance_macd bm1 " +
            "INNER JOIN ( " +
            "    SELECT symbol, MAX(timestamp) AS max_timestamp " +
            "    FROM binance_macd " +
            "    WHERE symbol IN :symbols " +
            "    GROUP BY symbol " +
            ") bm2 ON bm1.symbol = bm2.symbol AND bm1.timestamp = bm2.max_timestamp",
            nativeQuery = true)
    List<BinanceMacd> findLatestForSymbol(@Param("symbols") List<String> symbols);

}
