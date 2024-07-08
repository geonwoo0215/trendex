package com.trendex.trendex.domain.candle.coinonecandle.repository;

import com.trendex.trendex.domain.candle.CryptoVolume;
import com.trendex.trendex.domain.candle.coinonecandle.model.CoinoneCandle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoinoneCandleRepository extends JpaRepository<CoinoneCandle, Long> {

    @Query("SELECT new com.trendex.trendex.domain.candle.CryptoVolume(c.volume) " +
            "FROM CoinoneCandle c " +
            "WHERE c.symbol = :symbol " +
            "AND c.timestamp BETWEEN :start AND :end " +
            "ORDER BY c.timestamp ASC")
    List<CryptoVolume> findVolumeBySymbolAndTimeRange(@Param("symbol") String symbol, @Param("start") long start, @Param("end") long end);

}
