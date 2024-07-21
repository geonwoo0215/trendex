package com.trendex.trendex.domain.binancecandle.facade;

import com.trendex.trendex.domain.binancecandle.model.BinanceCandle;
import com.trendex.trendex.domain.binancecandle.service.BinanceCandleFetchService;
import com.trendex.trendex.domain.binancecandle.service.BinanceCandleService;
import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.binancesymbol.service.BinanceSymbolService;
import com.trendex.trendex.domain.candle.CandleAnalysisTime;
import com.trendex.trendex.domain.candle.CryptoClosePrice;
import com.trendex.trendex.domain.candle.CryptoClosePrices;
import com.trendex.trendex.domain.macd.binancemacd.model.BinanceMacd;
import com.trendex.trendex.domain.macd.binancemacd.service.BinanceMacdService;
import com.trendex.trendex.domain.rsi.binancersi.model.BinanceRsi;
import com.trendex.trendex.domain.rsi.binancersi.service.BinanceRsiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class BinanceCandleFacade {

    private final BinanceCandleService binanceCandleService;

    private final BinanceSymbolService binanceSymbolService;

    private final BinanceCandleFetchService binanceCandleFetchService;

    private final BinanceMacdService binanceMacdService;

    private final BinanceRsiService binanceRsiService;

    //    @Scheduled(cron = "0 */5 * * * *")
    @Scheduled(fixedRate = 60000000)
    public void fetchAndSaveBinanceData() {

        List<BinanceSymbol> binanceSymbols = binanceSymbolService.findAll();

        Flux<BinanceCandle> binanceCandleFlux = binanceCandleFetchService.fetchBinanceData(binanceSymbols).share();
        binanceCandleService.saveAll(binanceCandleFlux)
                .then(Mono.zip(
                        Mono.fromRunnable(() -> saveRsi(binanceSymbols))
                                .subscribeOn(Schedulers.boundedElastic()),
                        Mono.fromRunnable(() -> saveMacd(binanceSymbols))
                                .subscribeOn(Schedulers.boundedElastic())
                ))
                .subscribe();
    }

    public void saveMacd(List<BinanceSymbol> binanceSymbols) {
        List<BinanceMacd> binanceMacds = binanceSymbols
                .parallelStream()
                .map(binanceSymbol -> {
                    List<CryptoClosePrice> closePriceList26 = binanceCandleService.getClosePricesBySymbolAndTime(binanceSymbol.getSymbol(), CandleAnalysisTime.MACD_TWENTY_SIX_TIME_STAMP.getTime());
                    CryptoClosePrices cryptoClosePrices26 = new CryptoClosePrices(closePriceList26);

                    List<CryptoClosePrice> closePriceList12 = cryptoClosePrices26.getClosePriceValues(CandleAnalysisTime.MACD_TWELVE_TIME_STAMP);
                    CryptoClosePrices cryptoClosePrices12 = new CryptoClosePrices(closePriceList12);

                    List<Double> macdValues = binanceMacdService.findAllBySymbolAndTimeStamp(binanceSymbol.getSymbol(), CandleAnalysisTime.MACD_NINE_TIME_STAMP.getTime());

                    List<Double> closePriceValues26 = cryptoClosePrices26.getClosePriceValues();
                    List<Double> closePriceValues12 = cryptoClosePrices12.getClosePriceValues();

                    return BinanceMacd.of(binanceSymbol.getSymbol(), closePriceValues26, closePriceValues12, macdValues);
                })
                .filter(Objects::nonNull)
                .toList();

        binanceMacdService.saveAll(binanceMacds);

    }

    public void saveRsi(List<BinanceSymbol> binanceSymbols) {

        List<BinanceRsi> binanceRsis = binanceSymbols
                .parallelStream()
                .map(binanceSymbol -> {
                    List<CryptoClosePrice> closePriceList = binanceCandleService.getClosePricesBySymbolAndTime(binanceSymbol.getSymbol(), CandleAnalysisTime.RSI_FOURTEEN_TIME_STAMP.getTime());
                    CryptoClosePrices cryptoClosePrices = new CryptoClosePrices(closePriceList);
                    return BinanceRsi.of(binanceSymbol.getSymbol(), cryptoClosePrices.getClosePriceValues());
                })
                .filter(Objects::nonNull)
                .toList();

        binanceRsiService.saveAll(binanceRsis);
    }
}
