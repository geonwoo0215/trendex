package com.trendex.trendex.domain.candle.upbitcandle.repository;

import com.trendex.trendex.domain.candle.CryptoVolume;
import com.trendex.trendex.domain.candle.upbitcandle.model.UpbitCandle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UpbitCandleRepository extends JpaRepository<UpbitCandle, Long> {
    List<CryptoVolume> findByMarketAndCandleDateTimeKstBetweenOrderByCandleDateTimeKstAsc(
            String market,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    );

}
