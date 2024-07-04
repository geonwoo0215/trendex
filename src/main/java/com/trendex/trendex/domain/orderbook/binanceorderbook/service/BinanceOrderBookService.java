package com.trendex.trendex.domain.orderbook.binanceorderbook.service;

import com.trendex.trendex.domain.orderbook.binanceorderbook.model.BinanceOrderBook;
import com.trendex.trendex.domain.orderbook.binanceorderbook.repository.BinanceOrderBookJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BinanceOrderBookService {

    private final BinanceOrderBookJdbcRepository binanceOrderBookJdbcRepository;

    @Transactional
    public void saveAll(List<BinanceOrderBook> binanceOrderBooks) {
        binanceOrderBookJdbcRepository.batchInsert(binanceOrderBooks);
    }


}
