package com.trendex.trendex.domain.symbol.coinonesymbol.facade;

import com.trendex.trendex.domain.symbol.coinonesymbol.model.CoinoneSymbol;
import com.trendex.trendex.domain.symbol.coinonesymbol.service.CoinoneSymbolFetchService;
import com.trendex.trendex.domain.symbol.coinonesymbol.service.CoinoneSymbolService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinoneSymbolFacade {

    private final CoinoneSymbolFetchService coinoneSymbolFetchService;

    private final CoinoneSymbolService coinoneSymbolService;

    @Scheduled(cron = "0 0 0 * * SUN")
    public void fetchAndSaveCoinoneData() {
        List<CoinoneSymbol> coinoneSymbols = coinoneSymbolFetchService.fetchCoinoneData();
        coinoneSymbolService.saveAll(coinoneSymbols);
    }
}
