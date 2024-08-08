package com.trendex.trendex.domain.trade.upbittrade.service;

import com.trendex.trendex.domain.trade.upbittrade.model.UpbitTrade;
import com.trendex.trendex.domain.trade.upbittrade.repository.UpbitTradeJdbcRepository;
import com.trendex.trendex.domain.trade.upbittrade.repository.UpbitTradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitTradeService {

    private final UpbitTradeJdbcRepository upbitTradeJdbcRepository;

    private final UpbitTradeRepository upbitTradeRepository;

    @Transactional
    public void saveAll(List<UpbitTrade> upbitTrades) {
        upbitTradeJdbcRepository.batchInsert(upbitTrades);
    }


}
