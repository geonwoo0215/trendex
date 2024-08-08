package com.trendex.trendex.domain.orderbooktradeaggregate.repository;

import com.trendex.trendex.config.DataLoader;
import com.trendex.trendex.domain.orderbooktradeaggregate.fixture.OrderBookTradeAggregateFixture;
import com.trendex.trendex.domain.orderbooktradeaggregate.model.OrderbookTradeAggregate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
@Import(DataLoader.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderbookTradeAggregateRepositoryTest {


    @Autowired
    OrderbookTradeAggregateRepository orderbookTradeAggregateRepository;

    @Test
    void 전체_거래량_매도매수량_조회_성공() {


        String market = "KRW-BTC";
        LocalDateTime startTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        OrderbookTradeAggregate orderbookTradeAggregate = OrderBookTradeAggregateFixture.createOrderbookTradeAggregate(market, startTime);
        orderbookTradeAggregateRepository.save(orderbookTradeAggregate);

        Optional<OrderbookTradeAggregate> orderbookTradeAggregateByMarketAndStartTime = orderbookTradeAggregateRepository.findOrderbookTradeAggregateByMarketAndStartTime(market, startTime);

        Assertions.assertThat(orderbookTradeAggregateByMarketAndStartTime).isPresent();
        Assertions.assertThat(orderbookTradeAggregateByMarketAndStartTime.get())
                .hasFieldOrPropertyWithValue("market", market)
                .hasFieldOrPropertyWithValue("startTime", startTime);

    }

}