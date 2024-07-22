package com.trendex.trendex.domain.upbitcandle.repository;

import com.trendex.trendex.domain.candle.CryptoClosePrice;
import com.trendex.trendex.domain.candle.CryptoVolume;
import com.trendex.trendex.domain.upbitcandle.model.UpbitCandle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UpbitCandleRepository extends JpaRepository<UpbitCandle, Long> {
    @Query("SELECT new com.trendex.trendex.domain.candle.CryptoVolume(u.volume) " +
            "FROM UpbitCandle u " +
            "WHERE u.market = :market " +
            "AND u.timestamp BETWEEN :start AND :end " +
            "ORDER BY u.timestamp ASC")
    List<CryptoVolume> findVolumeByMarketAndTimeRange(@Param("market") String market, @Param("start") long startDateTime, @Param("end") long endDateTime);

    @Query("SELECT new com.trendex.trendex.domain.candle.CryptoClosePrice(u.closePrice,u.timestamp) " +
            "FROM UpbitCandle u " +
            "WHERE u.market = :market " +
            "AND u.timestamp >= :start " +
            "ORDER BY u.timestamp ASC")
    List<CryptoClosePrice> findClosePriceByMarketAndTime(@Param("market") String market, @Param("start") long start);


}
