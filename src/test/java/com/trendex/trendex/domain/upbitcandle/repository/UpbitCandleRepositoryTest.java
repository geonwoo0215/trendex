package com.trendex.trendex.domain.upbitcandle.repository;

import com.trendex.trendex.config.DataLoader;
import com.trendex.trendex.domain.candle.CryptoClosePrice;
import com.trendex.trendex.domain.candle.CryptoVolume;
import com.trendex.trendex.domain.upbitcandle.fixture.UpbitCandleFixture;
import com.trendex.trendex.domain.upbitcandle.model.UpbitCandle;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@DataJpaTest
@Import(DataLoader.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UpbitCandleRepositoryTest {

    @Autowired
    UpbitCandleRepository upbitCandleRepository;


    @Test
    void 거래_시장과_시간_범위로_거래량_조회() {

        List<String> markets = List.of("KRW-BTC", "KRW-ADA", "KRW-SOR");

        Long startTime = 0L;
        Long endTime = 5L;
        String volume = "4";
        List<UpbitCandle> upbitCandles = UpbitCandleFixture.createUpbitCandles(markets, endTime, volume);

        upbitCandleRepository.saveAll(upbitCandles);

        String btcMarket = "KRW-BTC";
        List<CryptoVolume> cryptoVolumes = upbitCandleRepository.findVolumeByMarketAndTimeRange(btcMarket, startTime, endTime);

        cryptoVolumes
                .forEach(cryptoVolume -> {
                    Assertions.assertThat(cryptoVolume.getVolume()).isEqualTo(volume);
                });

    }

    @Test
    void 거래_시장과_시간_시작점으로_거래량_조회() {

        String btcMarket = "KRW-BTC";

        Long prevTime = -1L;
        Long startTime = 0L;
        Long endTime = 5L;
        Double tradePrice = 15.0;
        UpbitCandle upbitCandle1 = UpbitCandleFixture.createUpbitCandle(btcMarket, endTime, tradePrice);
        UpbitCandle upbitCandle2 = UpbitCandleFixture.createUpbitCandle(btcMarket, prevTime, tradePrice);

        upbitCandleRepository.save(upbitCandle1);
        upbitCandleRepository.save(upbitCandle2);

        List<CryptoClosePrice> cryptoClosePrices = upbitCandleRepository.findClosePriceByMarketAndTime(btcMarket, startTime);

        cryptoClosePrices
                .forEach(cryptoClosePrice -> Assertions.assertThat(cryptoClosePrice.getClosePrice()).isEqualTo(tradePrice));

        Assertions.assertThat(cryptoClosePrices).hasSize(1);

    }


}