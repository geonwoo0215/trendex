package com.trendex.trendex.domain.binancesymbol.facade;

import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.binancesymbol.service.BinanceSymbolFetchService;
import com.trendex.trendex.domain.binancesymbol.service.BinanceSymbolService;
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
