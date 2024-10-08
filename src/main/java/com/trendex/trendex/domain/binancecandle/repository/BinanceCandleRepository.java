package com.trendex.trendex.domain.binancecandle.repository;

import com.trendex.trendex.domain.binancecandle.model.BinanceCandle;
import com.trendex.trendex.domain.candle.CryptoClosePrice;
import com.trendex.trendex.domain.candle.CryptoVolume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BinanceCandleRepository extends JpaRepository<BinanceCandle, Long> {

    @Query("SELECT new com.trendex.trendex.domain.candle.CryptoVolume(b.volume) " +
            "FROM BinanceCandle b " +
            "WHERE b.symbol = :symbol " +
            "AND b.klineCloseTime BETWEEN :start AND :end " +
            "ORDER BY b.klineCloseTime ASC")
    List<CryptoVolume> findVolumeBySymbolAndTimeRange(@Param("symbol") String symbol, @Param("start") long start, @Param("end") long end);

    @Query("SELECT new com.trendex.trendex.domain.candle.CryptoClosePrice(b.closePrice, b.klineCloseTime) " +
            "FROM BinanceCandle b " +
            "WHERE b.symbol = :symbol " +
            "AND b.klineCloseTime <= :end " +
            "ORDER BY b.klineCloseTime ASC")
    List<CryptoClosePrice> findTradePriceBySymbolAndTime(@Param("symbol") String symbol, @Param("end") long end);


}
