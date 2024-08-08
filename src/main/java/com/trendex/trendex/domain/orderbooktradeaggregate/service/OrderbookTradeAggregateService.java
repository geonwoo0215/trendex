package com.trendex.trendex.domain.orderbooktradeaggregate.service;

import com.trendex.trendex.domain.orderbooktradeaggregate.dto.response.OrderbookTradeAggregateResponse;
import com.trendex.trendex.domain.orderbooktradeaggregate.model.OrderbookTradeAggregate;
import com.trendex.trendex.domain.orderbooktradeaggregate.repository.OrderbookTradeAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderbookTradeAggregateService {

    private final OrderbookTradeAggregateRepository orderbookTradeAggregateRepository;

    public OrderbookTradeAggregateResponse findOrderbookTradeAggregateByMarketAndTimestamp(String market, LocalDateTime startTime) {

        OrderbookTradeAggregate orderbookTradeAggregate = orderbookTradeAggregateRepository.findOrderbookTradeAggregateByMarketAndStartTime(market, startTime)
                .orElseThrow(RuntimeException::new);

        OrderbookTradeAggregateResponse orderbookTradeAggregateResponse = orderbookTradeAggregate.toOrderbookTradeAggregateResponse();

        return orderbookTradeAggregateResponse;

    }

}
