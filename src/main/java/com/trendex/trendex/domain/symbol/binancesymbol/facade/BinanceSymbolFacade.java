package com.trendex.trendex.domain.symbol.binancesymbol.facade;

import com.trendex.trendex.domain.symbol.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.symbol.binancesymbol.service.BinanceSymbolFetchService;
import com.trendex.trendex.domain.symbol.binancesymbol.service.BinanceSymbolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BinanceSymbolFacade {

    private final BinanceSymbolFetchService binanceSymbolFetchService;

    private final BinanceSymbolService binanceSymbolService;

    //    @Scheduled(cron = "0 0 0 * * SUN")
    public void fetchAndSaveBinanceData() {
        List<BinanceSymbol> binanceSymbols = binanceSymbolFetchService.fetchBinanceData();
        binanceSymbolService.saveAll(binanceSymbols);
    }
}
