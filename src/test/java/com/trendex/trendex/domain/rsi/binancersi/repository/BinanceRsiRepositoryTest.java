package com.trendex.trendex.domain.rsi.binancersi.repository;

import com.trendex.trendex.config.DataLoader;
import com.trendex.trendex.domain.binancesymbol.repository.BinanceSymbolRepository;
import com.trendex.trendex.domain.rsi.binancersi.fixture.BinanceRsiFixture;
import com.trendex.trendex.domain.rsi.binancersi.model.BinanceRsi;
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
class BinanceRsiRepositoryTest {

    @Autowired
    BinanceSymbolRepository binanceSymbolRepository;

    @Autowired
    BinanceRsiRepository binanceRsiRepository;

    @Test
    void 거래_시장별로_가장_최근의_RSI_값_조회() {

        List<String> symbols = List.of("KRW-BTC", "KRW-ADA", "KRW-SOR");

        Long latestTime = 5L;

        List<BinanceRsi> binanceRsis = BinanceRsiFixture.createBinanceRsis(symbols, latestTime);

        binanceRsiRepository.saveAll(binanceRsis);

        List<BinanceRsi> latestForSymbols = binanceRsiRepository.findLatestForSymbols(symbols);

        latestForSymbols
                .forEach(upbitRsi -> {
                    Assertions.assertThat(upbitRsi.getTimestamp()).isEqualTo(latestTime);
                });

    }

}