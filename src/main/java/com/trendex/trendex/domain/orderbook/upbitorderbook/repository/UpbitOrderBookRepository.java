package com.trendex.trendex.domain.orderbook.upbitorderbook.repository;

import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpbitOrderBookRepository extends JpaRepository<UpbitOrderBook, Long> {
}
