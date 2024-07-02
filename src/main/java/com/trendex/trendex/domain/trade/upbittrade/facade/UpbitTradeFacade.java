package com.trendex.trendex.domain.trade.upbittrade.facade;

import com.trendex.trendex.domain.symbol.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.domain.symbol.upbitsymbol.service.UpbitSymbolService;
import com.trendex.trendex.domain.trade.upbittrade.model.UpbitTrade;
import com.trendex.trendex.domain.trade.upbittrade.service.UpbitTradeFetchService;
import com.trendex.trendex.domain.trade.upbittrade.service.UpbitTradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitTradeFacade {

    private final UpbitTradeService upbitTradeService;

    private final UpbitTradeFetchService upbitTradeFetchService;

    private final UpbitSymbolService upbitSymbolService;

    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveUpbitData() {
        List<UpbitSymbol> upbitSymbols = upbitSymbolService.findAll();
        List<UpbitTrade> upbitTrades = upbitTradeFetchService.fetchUpbitData(upbitSymbols);
        upbitTradeService.saveAll(upbitTrades);
        log.info("upbit fetch done");
    }

}
