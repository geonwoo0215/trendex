package com.trendex.trendex.domain.macd.upbitmacd.repository;

import com.trendex.trendex.config.DataLoader;
import com.trendex.trendex.domain.macd.upbitmacd.fixture.UpbitMacdFixture;
import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacd;
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
class UpbitMacdRepositoryTest {

    @Autowired
    UpbitMacdRepository upbitMacdRepository;

    @Test
    void 거래_시장과_시간_범위로_거래량_조회() {

        List<String> markets = List.of("KRW-BTC", "KRW-ADA", "KRW-SOR");
        Long startTime = 0L;
        Long endTime = 5L;
        String btcMarket = "KRW-BTC";
        List<UpbitMacd> upbitMacds = UpbitMacdFixture.createUpbitMacds(markets, endTime);

        upbitMacdRepository.saveAll(upbitMacds);
        List<UpbitMacd> macdByMarketAndTime = upbitMacdRepository.findMacdByMarketAndTime(btcMarket, startTime);

        macdByMarketAndTime
                .forEach(upbitMacd -> {
                    Assertions.assertThat(upbitMacd.getMarket()).isEqualTo(btcMarket);
                    Assertions.assertThat(upbitMacd.getTimestamp()).isGreaterThanOrEqualTo(startTime);
                });
    }

    @Test
    void 거래_시장별로_가장_최근의_MACD_값_조회() {

        List<String> markets = List.of("KRW-BTC", "KRW-ADA", "KRW-SOR");
        Long endTime = 5L;
        List<UpbitMacd> upbitMacds = UpbitMacdFixture.createUpbitMacds(markets, endTime);

        upbitMacdRepository.saveAll(upbitMacds);

        List<UpbitMacd> latestForMarkets = upbitMacdRepository.findLatestForMarkets(markets);

        latestForMarkets
                .forEach(upbitMacd -> {
                    Assertions.assertThat(upbitMacd.getTimestamp()).isEqualTo(endTime);
                });

    }

}