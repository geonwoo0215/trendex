package com.trendex.trendex.domain.upbitmarket.repository;

import com.trendex.trendex.config.DataLoader;
import com.trendex.trendex.domain.upbitmarket.fixture.UpbitMarketFixture;
import com.trendex.trendex.domain.upbitmarket.model.UpbitMarket;
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
class UpbitMarketRepositoryTest {

    @Autowired
    DataLoader dataLoader;

    @Autowired
    UpbitMarketRepository upbitMarketRepository;

    @Test
    void KRW_코인거래시장_조회() {

        List<String> markets = List.of("KRW-BTC", "KRW-ADA", "KRW-SOR");

        List<UpbitMarket> upbitMarkets = UpbitMarketFixture.createUpbitMarkets(markets);

        upbitMarketRepository.saveAll(upbitMarkets);

        List<UpbitMarket> marketsStartWithKRW = upbitMarketRepository.findMarketsStartWithKRW();

        marketsStartWithKRW
                .forEach(upbitMarket -> {
                    Assertions.assertThat(upbitMarket.getMarket().startsWith("KRW")).isTrue();
                });

    }

}