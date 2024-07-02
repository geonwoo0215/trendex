package com.trendex.trendex.domain.trade.binancetrade.facade;

import com.trendex.trendex.domain.symbol.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.symbol.binancesymbol.service.BinanceSymbolService;
import com.trendex.trendex.domain.trade.binancetrade.model.BinanceTrade;
import com.trendex.trendex.domain.trade.binancetrade.service.BinanceTradeFetchService;
import com.trendex.trendex.domain.trade.binancetrade.service.BinanceTradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BinanceTradeFacade {

    private final BinanceTradeService binanceTradeService;

    private final BinanceTradeFetchService binanceTradeFetchService;

    private final BinanceSymbolService binanceSymbolService;

    public void fetchAndSaveBinanceData() {
        List<BinanceSymbol> binanceSymbols = binanceSymbolService.findAll();
        List<BinanceTrade> binanceTrades = binanceTradeFetchService.fetchBinanceData(binanceSymbols);
        binanceTradeService.saveAll(binanceTrades);
        log.info("binance fetch done");
    }

}
