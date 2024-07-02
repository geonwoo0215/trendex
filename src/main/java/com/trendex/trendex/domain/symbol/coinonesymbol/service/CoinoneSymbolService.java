package com.trendex.trendex.domain.symbol.coinonesymbol.service;

import com.trendex.trendex.domain.symbol.coinonesymbol.model.CoinoneSymbol;
import com.trendex.trendex.domain.symbol.coinonesymbol.repository.CoinoneSymbolJdbcRepository;
import com.trendex.trendex.domain.symbol.coinonesymbol.repository.CoinoneSymbolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinoneSymbolService {

    private final CoinoneSymbolRepository coinoneSymbolRepository;

    private final CoinoneSymbolJdbcRepository coinoneSymbolJdbcRepository;

    @Transactional(readOnly = true)
    public List<CoinoneSymbol> findAll() {
        return coinoneSymbolRepository.findAll();
    }

    @Transactional
    public void saveAll(List<CoinoneSymbol> coinoneSymbols) {
        coinoneSymbolJdbcRepository.batchInsert(coinoneSymbols);
    }
}
