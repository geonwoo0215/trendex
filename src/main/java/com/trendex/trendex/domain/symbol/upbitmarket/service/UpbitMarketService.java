package com.trendex.trendex.domain.symbol.upbitmarket.service;

import com.trendex.trendex.domain.symbol.upbitmarket.model.UpbitMarket;
import com.trendex.trendex.domain.symbol.upbitmarket.repository.UpbitMarketJdbcRepository;
import com.trendex.trendex.domain.symbol.upbitmarket.repository.UpbitMarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitMarketService {

    private final UpbitMarketRepository upbitMarketRepository;

    private final UpbitMarketJdbcRepository upbitMarketJdbcRepository;

    @Transactional(readOnly = true)
    public List<UpbitMarket> findAll() {
        return upbitMarketRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<UpbitMarket> findMarketsStartingWith(String prefix) {
        return upbitMarketRepository.findMarketsStartingWith(prefix);
    }

    @Transactional
    public void saveAll(List<UpbitMarket> upbitSymbols) {
        upbitMarketJdbcRepository.batchInsert(upbitSymbols);
    }
}
