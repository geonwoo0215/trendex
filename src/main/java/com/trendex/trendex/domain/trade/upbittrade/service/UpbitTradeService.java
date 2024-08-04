package com.trendex.trendex.domain.trade.upbittrade.service;

import com.trendex.trendex.domain.trade.upbittrade.dto.MarketAggregateDto;
import com.trendex.trendex.domain.trade.upbittrade.model.UpbitTrade;
import com.trendex.trendex.domain.trade.upbittrade.repository.UpbitTradeJdbcRepository;
import com.trendex.trendex.domain.trade.upbittrade.repository.UpbitTradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitTradeService {

    private final UpbitTradeJdbcRepository upbitTradeJdbcRepository;

    private final UpbitTradeRepository upbitTradeRepository;

    @Transactional
    public void saveAll(List<UpbitTrade> upbitTrades) {
        upbitTradeJdbcRepository.batchInsert(upbitTrades);
    }

    @Transactional(readOnly = true)
    public MarketAggregateDto findAggregatedMarketData(String market, Long startTime) {
        LocalDateTime startDateTime = new Timestamp(startTime).toLocalDateTime();

        LocalDateTime endDateTime = startDateTime.plusHours(1);

        Long endTime = Timestamp.valueOf(endDateTime).getTime();

        return upbitTradeRepository.findAggregatedMarketData(market, startTime, endTime)
                .orElseThrow(RuntimeException::new);
    }

}
