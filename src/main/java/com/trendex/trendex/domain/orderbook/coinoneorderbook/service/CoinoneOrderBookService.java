package com.trendex.trendex.domain.orderbook.coinoneorderbook.service;

import com.trendex.trendex.domain.orderbook.coinoneorderbook.model.CoinoneOrderBook;
import com.trendex.trendex.domain.orderbook.coinoneorderbook.repository.CoinoneOrderBookJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinoneOrderBookService {

    private final CoinoneOrderBookJdbcRepository coinoneOrderBookJdbcRepository;

    @Transactional
    public void saveAll(List<CoinoneOrderBook> coinoneOrderBooks) {
        coinoneOrderBookJdbcRepository.batchInsert(coinoneOrderBooks);
    }

}
