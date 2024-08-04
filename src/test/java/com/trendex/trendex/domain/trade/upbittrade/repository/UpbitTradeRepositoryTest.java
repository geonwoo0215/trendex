package com.trendex.trendex.domain.trade.upbittrade.repository;

import com.trendex.trendex.config.DataLoader;
import com.trendex.trendex.domain.orderbook.upbitorderbook.fiture.UpbitOrderBookFixture;
import com.trendex.trendex.domain.orderbook.upbitorderbook.fiture.UpbitOrderBookUnitFixture;
import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBook;
import com.trendex.trendex.domain.orderbook.upbitorderbook.repository.UpbitOrderBookRepository;
import com.trendex.trendex.domain.trade.upbittrade.dto.MarketAggregateDto;
import com.trendex.trendex.domain.trade.upbittrade.fiture.UpbitTradeFixture;
import com.trendex.trendex.domain.trade.upbittrade.model.UpbitTrade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Import(DataLoader.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UpbitTradeRepositoryTest {

    @Autowired
    UpbitTradeRepository upbitTradeRepository;

    @Autowired
    UpbitOrderBookRepository upbitOrderBookRepository;

    @Autowired
    DataLoader dataLoader;

    @Test
    @Rollback(value = false)
    void 거래_시장과_시간_범위로_매도_매수_거래량_조회() {

        String btcMarket = "KRW-BTC";
        String adaMarket = "KRW-ADA";
        String solMarket = "KRW-SOL";

        List<UpbitOrderBook> upbitBTCOrderBooks = UpbitOrderBookFixture.createUpbitOrderBookUnits(10L, btcMarket);
        upbitBTCOrderBooks
                .forEach(upbitOrderBook -> {
                    UpbitOrderBookUnitFixture.createUpbitOrderBookUnits()
                            .forEach(upbitOrderBook::addUpbitOrderBookUnits);
                });

        List<UpbitOrderBook> upbitADAOrderBooks = UpbitOrderBookFixture.createUpbitOrderBookUnits(10L, adaMarket);
        upbitADAOrderBooks
                .forEach(upbitOrderBook -> {
                    UpbitOrderBookUnitFixture.createUpbitOrderBookUnits()
                            .forEach(upbitOrderBook::addUpbitOrderBookUnits);
                });


        List<UpbitOrderBook> upbitSOLOrderBooks = UpbitOrderBookFixture.createUpbitOrderBookUnits(10L, solMarket);
        upbitSOLOrderBooks
                .forEach(upbitOrderBook -> {
                    UpbitOrderBookUnitFixture.createUpbitOrderBookUnits()
                            .forEach(upbitOrderBook::addUpbitOrderBookUnits);
                });

        List<UpbitTrade> upbitBTCTrades = UpbitTradeFixture.createUpbitTrades(10L, btcMarket);
        List<UpbitTrade> upbitADATrades = UpbitTradeFixture.createUpbitTrades(10L, adaMarket);
        List<UpbitTrade> upbitSOLTrades = UpbitTradeFixture.createUpbitTrades(10L, solMarket);

        upbitOrderBookRepository.saveAll(upbitBTCOrderBooks);
        upbitOrderBookRepository.saveAll(upbitADAOrderBooks);
        upbitOrderBookRepository.saveAll(upbitSOLOrderBooks);
        upbitTradeRepository.saveAll(upbitBTCTrades);
        upbitTradeRepository.saveAll(upbitADATrades);
        upbitTradeRepository.saveAll(upbitSOLTrades);


        Long startTime = Timestamp.valueOf(LocalDateTime.now()).getTime();
        Long endTime = Timestamp.valueOf(LocalDateTime.now().plusHours(1L)).getTime();


        Optional<MarketAggregateDto> aggregatedMarketData = upbitTradeRepository.findAggregatedMarketData(btcMarket, startTime, endTime);

        Assertions.assertThat(aggregatedMarketData.get()).isNotNull();

    }

}