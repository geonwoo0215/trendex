package com.trendex.trendex.domain.symbol.bithumbsymbol.facade;

import com.trendex.trendex.domain.symbol.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.domain.symbol.bithumbsymbol.service.BithumbSymbolFetchService;
import com.trendex.trendex.domain.symbol.bithumbsymbol.service.BithumbSymbolService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BithumbSymbolFacade {

    private final BithumbSymbolFetchService bithumbSymbolFetchService;

    private final BithumbSymbolService bithumbSymbolService;

    @Scheduled(cron = "0 0 0 * * SUN")
    public void fetchAndSaveBithumbData() {
        List<BithumbSymbol> bithumbSymbols = bithumbSymbolFetchService.fetchBithumbData();
        bithumbSymbolService.saveAll(bithumbSymbols);
    }

}
