package com.trendex.trendex.domain.trade.coinonetrade.facade;

import com.trendex.trendex.domain.symbol.coinonesymbol.model.CoinoneSymbol;
import com.trendex.trendex.domain.symbol.coinonesymbol.service.CoinoneSymbolService;
import com.trendex.trendex.domain.trade.coinonetrade.model.CoinoneTrade;
import com.trendex.trendex.domain.trade.coinonetrade.service.CoinoneTradeFetchService;
import com.trendex.trendex.domain.trade.coinonetrade.service.CoinoneTradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoinoneTradeFacade {

    private final CoinoneTradeService coinoneTradeService;

    private final CoinoneTradeFetchService coinoneTradeFetchService;

    private final CoinoneSymbolService coinoneSymbolService;

    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveCoinoneData() {
        List<CoinoneSymbol> coinoneSymbols = coinoneSymbolService.findAll();
        List<CoinoneTrade> coinoneTrades = coinoneTradeFetchService.fetchCoinoneData(coinoneSymbols);
        coinoneTradeService.saveAll(coinoneTrades);
        log.info("coinone fetch done");
    }
}
