package com.trendex.trendex.domain.macd.binancemacd.repository;

import com.trendex.trendex.config.DataLoader;
import com.trendex.trendex.domain.macd.binancemacd.fixture.BinanceMacdFixture;
import com.trendex.trendex.domain.macd.binancemacd.model.BinanceMacd;
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
class BinanceMacdRepositoryTest {

    @Autowired
    BinanceMacdRepository binanceMacdRepository;

    @Test
    void 거래_시장과_시간_범위로_거래량_조회() {

        List<String> symbols = List.of("BTC", "ADA", "SOR");
        Long startTime = 0L;
        Long endTime = 5L;
        String btcMarket = "KRW-BTC";
        List<BinanceMacd> binanceMacds = BinanceMacdFixture.createBinanceMacds(symbols, endTime);

        binanceMacdRepository.saveAll(binanceMacds);
        List<BinanceMacd> macdBySymbolAndTime = binanceMacdRepository.findMacdBySymbolAndTime(btcMarket, startTime);

        macdBySymbolAndTime
                .forEach(upbitMacd -> {
                    Assertions.assertThat(upbitMacd.getSymbol()).isEqualTo(btcMarket);
                    Assertions.assertThat(upbitMacd.getTimestamp()).isGreaterThanOrEqualTo(startTime);
                });
    }

    @Test
    void 거래_시장별로_가장_최근의_MACD_값_조회() {

        List<String> symbols = List.of("BTC", "ADA", "SOR");
        Long endTime = 5L;
        List<BinanceMacd> binanceMacds = BinanceMacdFixture.createBinanceMacds(symbols, endTime);

        binanceMacdRepository.saveAll(binanceMacds);

        List<BinanceMacd> latestForSymbol = binanceMacdRepository.findLatestForSymbol(symbols);

        latestForSymbol
                .forEach(upbitMacd -> {
                    Assertions.assertThat(upbitMacd.getTimestamp()).isEqualTo(endTime);
                });

    }

}