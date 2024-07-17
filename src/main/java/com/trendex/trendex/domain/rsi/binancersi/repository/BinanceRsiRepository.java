package com.trendex.trendex.domain.rsi.binancersi.repository;

import com.trendex.trendex.domain.rsi.binancersi.model.BinanceRsi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BinanceRsiRepository extends JpaRepository<BinanceRsi, Long> {

    @Query("SELECT br " +
            "FROM BinanceRsi br " +
            "WHERE br.symbol = :symbol " +
            "ORDER BY br.timestamp DESC")
    Optional<BinanceRsi> findLatest(@Param("symbol") String symbol);

    @Query(value = "SELECT br1.id, br1.value, br1.market, br1.timestamp " +
            "FROM binance_rsi br1 " +
            "INNER JOIN ( " +
            "    SELECT symbol, MAX(timestamp) AS max_timestamp " +
            "    FROM binance_rsi " +
            "    WHERE symbol IN :markets " +
            "    GROUP BY symbol " +
            ") br2 ON br1.symbol = br2.symbol AND br1.timestamp = br2.max_timestamp",
            nativeQuery = true)
    List<BinanceRsi> findLatestForSymbols(@Param("symbols") List<String> symbols);


}
