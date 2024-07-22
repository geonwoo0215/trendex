package com.trendex.trendex.domain.orderbook.upbitorderbook.service;

import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBook;
import com.trendex.trendex.domain.orderbook.upbitorderbook.repository.UpbitOrderBookJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitOrderBookService {

    private final UpbitOrderBookJdbcRepository upbitOrderBookJdbcRepository;

    @Transactional
    public void saveAll(List<UpbitOrderBook> upbitOrderBooks) {
        upbitOrderBookJdbcRepository.batchInsert(upbitOrderBooks);
    }

}
