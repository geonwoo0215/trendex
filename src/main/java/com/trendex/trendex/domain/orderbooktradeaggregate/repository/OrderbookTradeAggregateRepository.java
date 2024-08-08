package com.trendex.trendex.domain.orderbooktradeaggregate.repository;

import com.trendex.trendex.domain.orderbooktradeaggregate.model.OrderbookTradeAggregate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderbookTradeAggregateRepository extends JpaRepository<OrderbookTradeAggregate, String> {

    Optional<OrderbookTradeAggregate> findOrderbookTradeAggregateByMarketAndStartTime(String market, LocalDateTime startTime);


}
