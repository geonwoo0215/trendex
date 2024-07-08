package com.trendex.trendex.domain.candle.bithumbcandle.repository;

import com.trendex.trendex.domain.candle.CryptoVolume;
import com.trendex.trendex.domain.candle.bithumbcandle.model.BithumbCandle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BithumbCandleRepository extends JpaRepository<BithumbCandle, Long> {
    @Query("SELECT new com.trendex.trendex.domain.candle.CryptoVolume(b.volume) " +
            "FROM BithumbCandle b " +
            "WHERE b.symbol = :symbol " +
            "AND b.timestamp BETWEEN :start AND :end " +
            "ORDER BY b.timestamp ASC")
    List<CryptoVolume> findVolumeBySymbolAndTimeRange(@Param("symbol") String symbol, @Param("start") long start, @Param("end") long end);
}
