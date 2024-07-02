package com.trendex.trendex.domain.symbol.bithumbsymbol.service;

import com.trendex.trendex.domain.symbol.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.domain.symbol.bithumbsymbol.repository.BithumbSymbolJdbcRepository;
import com.trendex.trendex.domain.symbol.bithumbsymbol.repository.BithumbSymbolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BithumbSymbolService {

    private final BithumbSymbolRepository bithumbSymbolRepository;

    private final BithumbSymbolJdbcRepository bithumbSymbolJdbcRepository;

    @Transactional(readOnly = true)
    public List<BithumbSymbol> findAll() {
        return bithumbSymbolRepository.findAll();
    }

    @Transactional
    public void saveAll(List<BithumbSymbol> bithumbSymbols) {
        bithumbSymbolJdbcRepository.batchInsert(bithumbSymbols);
    }
}
