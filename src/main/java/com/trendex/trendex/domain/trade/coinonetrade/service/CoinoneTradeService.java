package com.trendex.trendex.domain.trade.coinonetrade.service;

import com.trendex.trendex.domain.trade.coinonetrade.model.CoinoneTrade;
import com.trendex.trendex.domain.trade.coinonetrade.repository.CoinoneTradeJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinoneTradeService {

    private final CoinoneTradeJdbcRepository coinoneTradeJdbcRepository;

    @Transactional
    public void saveAll(List<CoinoneTrade> coinoneTrades) {
        coinoneTradeJdbcRepository.batchInsert(coinoneTrades);
    }

}
