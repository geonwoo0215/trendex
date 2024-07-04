package com.trendex.trendex.domain.orderbook.bithumborderbook.service;

import com.trendex.trendex.domain.orderbook.bithumborderbook.model.BithumbOrderBook;
import com.trendex.trendex.domain.orderbook.bithumborderbook.repository.BithumbOrderBookJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BithumbOrderBookService {

    private final BithumbOrderBookJdbcRepository bithumbOrderBookJdbcRepository;

    @Transactional
    public void saveAll(List<BithumbOrderBook> bithumbOrderBooks) {
        bithumbOrderBookJdbcRepository.batchInsert(bithumbOrderBooks);
    }
}
