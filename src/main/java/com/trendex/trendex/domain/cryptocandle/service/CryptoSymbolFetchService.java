package com.trendex.trendex.domain.cryptocandle.service;

import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.domain.coinonesymbol.model.CoinoneSymbol;
import com.trendex.trendex.domain.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.global.client.webclient.service.BinanceWebClientService;
import com.trendex.trendex.global.client.webclient.service.BithumbWebClientService;
import com.trendex.trendex.global.client.webclient.service.CoinoneWebClientService;
import com.trendex.trendex.global.client.webclient.service.UpbitWebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CryptoSymbolFetchService {

    private final BinanceWebClientService binanceWebClientService;

    private final BithumbWebClientService bithumbWebClientService;

    private final CoinoneWebClientService coinoneWebClientService;

    private final UpbitWebClientService upbitWebClientService;

    public List<BinanceSymbol> fetchBinanceData() {

        return binanceWebClientService.getTickerPrice()
                .flatMap(binanceTickerPrices -> Mono.just(
                        binanceTickerPrices.stream()
                                .map(binanceTickerPrice -> new BinanceSymbol(binanceTickerPrice.getSymbol()))
                                .collect(Collectors.toList())
                ))
                .block();

    }

    public List<BithumbSymbol> fetchBithumbData() {

        return bithumbWebClientService.getWithdrawMinimum("ALL")
                .flatMap(bithumbWithdrawMinimum -> {
                    List<BithumbSymbol> symbols = bithumbWithdrawMinimum.getData().stream()
                            .map(currencyData -> new BithumbSymbol(currencyData.getCurrency()))
                            .collect(Collectors.toList());
                    return Mono.just(symbols);
                })
                .block();

    }

    public List<CoinoneSymbol> fetchCoinoneData() {
        return coinoneWebClientService.getAllCurrencies()
                .flatMap(coinoneCurrency -> {
                    List<CoinoneSymbol> symbols = coinoneCurrency.getCurrencies().stream()
                            .map(coinoneCurrencyDetail -> new CoinoneSymbol(coinoneCurrencyDetail.getSymbol()))
                            .collect(Collectors.toList());
                    return Mono.just(symbols);
                })
                .block();
    }

    public List<UpbitSymbol> fetchUpbitData() {

        return upbitWebClientService.getMarketCode()
                .flatMap(upbitMarketCodes -> Mono.just(
                        upbitMarketCodes.stream()
                                .map(upbitMarketCode -> new UpbitSymbol(upbitMarketCode.getMarket()))
                                .collect(Collectors.toList())
                ))
                .block();

    }

}
