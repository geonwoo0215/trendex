package com.trendex.trendex.domain.candle.coinonecandle.repository;

import com.trendex.trendex.domain.candle.CryptoVolume;
import com.trendex.trendex.domain.candle.coinonecandle.model.CoinoneCandle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoinoneCandleRepository extends JpaRepository<CoinoneCandle, Long> {

    List<CryptoVolume> findBySymbolAndTimestampBetweenOrderByTimestampAsc(
            String symbol,
            long start,
            long end
    );
}
