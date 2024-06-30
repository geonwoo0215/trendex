package com.trendex.trendex.domain.bithumbsymbol.service;

import com.trendex.trendex.domain.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.domain.bithumbsymbol.repository.BithumbSymbolJdbcRepository;
import com.trendex.trendex.domain.bithumbsymbol.repository.BithumbSymbolRepository;
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
