package com.trendex.trendex.domain.trade.bithumbtrade.facade;

import com.trendex.trendex.domain.symbol.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.domain.symbol.bithumbsymbol.service.BithumbSymbolService;
import com.trendex.trendex.domain.trade.bithumbtrade.model.BithumbTrade;
import com.trendex.trendex.domain.trade.bithumbtrade.service.BithumbTradeFetchService;
import com.trendex.trendex.domain.trade.bithumbtrade.service.BithumbTradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BithumbTradeFacade {

    private final BithumbTradeService bithumbTradeService;

    private final BithumbSymbolService bithumbSymbolService;

    private final BithumbTradeFetchService bithumbTradeFetchService;

    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveBithumbData() {
        List<BithumbSymbol> bithumbSymbols = bithumbSymbolService.findAll();
        List<BithumbTrade> bithumbTrades = bithumbTradeFetchService.fetchBithumbData(bithumbSymbols);
        bithumbTradeService.saveAll(bithumbTrades);
        log.info("bithumb fetch done");
    }
}
