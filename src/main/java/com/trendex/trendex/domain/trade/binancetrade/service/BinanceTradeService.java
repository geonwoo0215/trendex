package com.trendex.trendex.domain.trade.binancetrade.service;

import com.trendex.trendex.domain.trade.binancetrade.model.BinanceTrade;
import com.trendex.trendex.domain.trade.binancetrade.repository.BinanceTradeJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BinanceTradeService {

    private final BinanceTradeJdbcRepository binanceTradeJdbcRepository;

    @Transactional
    public void saveAll(List<BinanceTrade> binanceTrades) {
        binanceTradeJdbcRepository.batchInsert(binanceTrades);
    }


}
