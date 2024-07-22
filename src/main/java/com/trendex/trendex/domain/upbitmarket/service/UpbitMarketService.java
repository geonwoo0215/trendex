package com.trendex.trendex.domain.upbitmarket.service;

import com.trendex.trendex.domain.upbitmarket.model.UpbitMarket;
import com.trendex.trendex.domain.upbitmarket.repository.UpbitMarketJdbcRepository;
import com.trendex.trendex.domain.upbitmarket.repository.UpbitMarketRepository;
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

    @Transactional
    public void saveAll(List<UpbitMarket> upbitSymbols) {
        upbitMarketRepository.saveAll(upbitSymbols);
    }

    @Transactional(readOnly = true)
    public List<UpbitMarket> findMarketsStartWithKRW() {
        return upbitMarketRepository.findMarketsStartWithKRW();
    }
}
