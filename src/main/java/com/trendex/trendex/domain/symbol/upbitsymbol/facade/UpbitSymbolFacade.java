package com.trendex.trendex.domain.symbol.upbitsymbol.facade;

import com.trendex.trendex.domain.symbol.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.domain.symbol.upbitsymbol.service.UpbitSymbolFetchService;
import com.trendex.trendex.domain.symbol.upbitsymbol.service.UpbitSymbolService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitSymbolFacade {

    private final UpbitSymbolFetchService upbitSymbolFetchService;

    private final UpbitSymbolService upbitSymbolService;

    @Scheduled(cron = "0 0 0 * * SUN")
    public void fetchAndSaveUpbitData() {
        List<UpbitSymbol> upbitSymbols = upbitSymbolFetchService.fetchUpbitData();
        upbitSymbolService.saveAll(upbitSymbols);
    }
}
