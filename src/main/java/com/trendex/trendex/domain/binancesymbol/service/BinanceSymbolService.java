package com.trendex.trendex.domain.binancesymbol.service;

import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.binancesymbol.repository.BinanceSymbolJdbcRepository;
import com.trendex.trendex.domain.binancesymbol.repository.BinanceSymbolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BinanceSymbolService {

    private final BinanceSymbolRepository binanceSymbolRepository;

    private final BinanceSymbolJdbcRepository binanceJdbcRepository;

    @Transactional(readOnly = true)
    public List<String> findAll() {
        return binanceSymbolRepository.findAll()
                .stream()
                .map(BinanceSymbol::getSymbol)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveAll(List<BinanceSymbol> binanceSymbols) {
        binanceSymbolRepository.saveAll(binanceSymbols);
    }
}
