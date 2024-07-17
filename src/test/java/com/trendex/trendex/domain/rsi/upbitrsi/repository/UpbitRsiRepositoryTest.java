package com.trendex.trendex.domain.rsi.upbitrsi.repository;

import com.trendex.trendex.config.DataLoader;
import com.trendex.trendex.domain.rsi.upbitrsi.fixture.UpbitRsiFixture;
import com.trendex.trendex.domain.rsi.upbitrsi.model.UpbitRsi;
import com.trendex.trendex.domain.upbitmarket.repository.UpbitMarketRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@Import(DataLoader.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UpbitRsiRepositoryTest {

    @Autowired
    DataLoader dataLoader;

    @Autowired
    UpbitMarketRepository upbitMarketRepository;

    @Autowired
    UpbitRsiRepository upbitRsiRepository;

    @Test
    @Rollback(value = false)
    void 더미데이터() {

        List<String> markets = List.of("KRW-BTC", "KRW-ADA", "KRW-SOR");

        Long latestTime = 5000L;

        List<UpbitRsi> upbitRsis = UpbitRsiFixture.createUpbitRsis(markets, latestTime);

        dataLoader.batchUpbitRsiInsert(upbitRsis);

    }

    @Test
    void 거래_시장별로_가장_최근의_RSI_값_조회() {

        List<String> markets = List.of("KRW-BTC", "KRW-ADA", "KRW-SOR");

        Long latestTime = 5L;

        List<UpbitRsi> upbitRsis = UpbitRsiFixture.createUpbitRsis(markets, latestTime);

        upbitRsiRepository.saveAll(upbitRsis);

        List<UpbitRsi> latestForMarkets = upbitRsiRepository.findLatestForMarkets(markets);

        latestForMarkets
                .forEach(upbitRsi -> {
                    Assertions.assertThat(upbitRsi.getTimestamp()).isEqualTo(latestTime);
                });

    }

}