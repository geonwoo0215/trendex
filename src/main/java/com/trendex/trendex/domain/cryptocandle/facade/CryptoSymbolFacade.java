package com.trendex.trendex.domain.cryptocandle.facade;

import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.binancesymbol.service.BinanceSymbolService;
import com.trendex.trendex.domain.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.domain.bithumbsymbol.service.BithumbSymbolService;
import com.trendex.trendex.domain.coinonesymbol.model.CoinoneSymbol;
import com.trendex.trendex.domain.coinonesymbol.service.CoinoneSymbolService;
import com.trendex.trendex.domain.cryptocandle.service.CryptoSymbolFetchService;
import com.trendex.trendex.domain.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.domain.upbitsymbol.service.UpbitSymbolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoSymbolFacade {

    private final CryptoSymbolFetchService cryptoSymbolFetchService;

    private final BithumbSymbolService bithumbSymbolService;

    private final BinanceSymbolService binanceSymbolService;

    private final CoinoneSymbolService coinoneSymbolService;

    private final UpbitSymbolService upbitSymbolService;

    public void fetchAndSaveBinanceData() {

        List<BinanceSymbol> binanceSymbols = cryptoSymbolFetchService.fetchBinanceData();
        binanceSymbolService.saveAll(binanceSymbols);
    }

    public void fetchAndSaveBithumbData() {
        List<BithumbSymbol> bithumbSymbols = cryptoSymbolFetchService.fetchBithumbData();
        bithumbSymbolService.saveAll(bithumbSymbols);
    }

    public void fetchAndSaveCoinoneData() {
        List<CoinoneSymbol> coinoneSymbols = cryptoSymbolFetchService.fetchCoinoneData();
        coinoneSymbolService.saveAll(coinoneSymbols);
    }

    public void fetchAndSaveUpbitData() {
        List<UpbitSymbol> upbitSymbols = cryptoSymbolFetchService.fetchUpbitData();
        upbitSymbolService.saveAll(upbitSymbols);
    }

}
