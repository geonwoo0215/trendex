package com.trendex.trendex.domain.trade.upbittrade.service;

import com.trendex.trendex.domain.trade.upbittrade.model.UpbitTrade;
import com.trendex.trendex.domain.trade.upbittrade.repository.UpbitTradeJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitTradeService {

    private final UpbitTradeJdbcRepository upbitTradeJdbcRepository;

    @Transactional
    public void saveAll(List<UpbitTrade> upbitTrades) {
        upbitTradeJdbcRepository.batchInsert(upbitTrades);
    }

}
