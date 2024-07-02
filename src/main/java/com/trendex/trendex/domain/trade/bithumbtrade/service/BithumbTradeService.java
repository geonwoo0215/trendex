package com.trendex.trendex.domain.trade.bithumbtrade.service;

import com.trendex.trendex.domain.trade.bithumbtrade.model.BithumbTrade;
import com.trendex.trendex.domain.trade.bithumbtrade.repository.BithumbTradeJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BithumbTradeService {

    private final BithumbTradeJdbcRepository bithumbTradeJdbcRepository;

    @Transactional
    public void saveAll(List<BithumbTrade> bithumbTrades) {
        bithumbTradeJdbcRepository.batchInsert(bithumbTrades);
    }

}
