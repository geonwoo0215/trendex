package com.trendex.trendex.domain.symbol.binancesymbol.service;

import com.trendex.trendex.domain.symbol.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.symbol.binancesymbol.repository.BinanceSymbolJdbcRepository;
import com.trendex.trendex.domain.symbol.binancesymbol.repository.BinanceSymbolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BinanceSymbolService {

    private final BinanceSymbolRepository binanceSymbolRepository;

    private final BinanceSymbolJdbcRepository binanceJdbcRepository;

    @Transactional(readOnly = true)
    public List<BinanceSymbol> findAll() {
        return binanceSymbolRepository.findAll();
    }

    @Transactional
    public void saveAll(List<BinanceSymbol> binanceSymbols) {
        binanceJdbcRepository.batchInsert(binanceSymbols);
    }
}
