package com.trendex.trendex.domain.candle.bithumbcandle.repository;

import com.trendex.trendex.domain.candle.CryptoVolume;
import com.trendex.trendex.domain.candle.bithumbcandle.model.BithumbCandle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BithumbCandleRepository extends JpaRepository<BithumbCandle, Long> {
    List<CryptoVolume> findBySymbolAndTimestampBetweenOrderByTimestampAsc(
            String symbol,
            long start,
            long end
    );
}
