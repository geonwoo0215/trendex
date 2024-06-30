package com.trendex.trendex.domain.upbitsymbol.service;

import com.trendex.trendex.domain.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.domain.upbitsymbol.repository.UpbitSymbolJdbcRepository;
import com.trendex.trendex.domain.upbitsymbol.repository.UpbitSymbolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitSymbolService {

    private final UpbitSymbolRepository upbitSymbolRepository;

    private final UpbitSymbolJdbcRepository upbitSymbolJdbcRepository;

    @Transactional(readOnly = true)
    public List<UpbitSymbol> findAll() {
        return upbitSymbolRepository.findAll();
    }

    @Transactional
    public void saveAll(List<UpbitSymbol> upbitSymbols) {
        upbitSymbolJdbcRepository.batchInsert(upbitSymbols);
    }
}
