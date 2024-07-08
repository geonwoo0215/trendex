package com.trendex.trendex.domain.candle.binancecandle.repository;

import com.trendex.trendex.domain.candle.CandleAnalysisTime;
import com.trendex.trendex.domain.candle.CryptoVolume;
import com.trendex.trendex.domain.candle.binancecandle.fixture.BinanceCandleFixture;
import com.trendex.trendex.domain.candle.binancecandle.model.BinanceCandle;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BinanceCandleRepositoryTest {

    @Autowired
    BinanceCandleRepository binanceCandleRepository;

    @Test
    void 코인_종류와_시간으로_거래량_조회() {

        List<BinanceCandle> binanceCandles = BinanceCandleFixture.createBinanceCandles(10);

        binanceCandleRepository.saveAll(binanceCandles);

        List<CryptoVolume> cryptoVolumes = binanceCandleRepository.findVolumeBySymbolAndTimeRange(binanceCandles.get(0).getSymbol(), CandleAnalysisTime.STAR_TIME_STAMP.getTime(), CandleAnalysisTime.END_TIME_STAMP.getTime());
        Assertions.assertThat(cryptoVolumes).hasSize(binanceCandles.size());

    }


}