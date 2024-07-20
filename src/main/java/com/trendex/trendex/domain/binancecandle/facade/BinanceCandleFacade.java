package com.trendex.trendex.domain.binancecandle.facade;

import com.trendex.trendex.domain.binancecandle.model.BinanceCandle;
import com.trendex.trendex.domain.binancecandle.service.BinanceCandleFetchService;
import com.trendex.trendex.domain.binancecandle.service.BinanceCandleService;
import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.binancesymbol.service.BinanceSymbolService;
import com.trendex.trendex.domain.candle.CandleAnalysisTime;
import com.trendex.trendex.domain.candle.CandleAnalysisUtil;
import com.trendex.trendex.domain.candle.CryptoClosePrice;
import com.trendex.trendex.domain.macd.binancemacd.model.BinanceMacd;
import com.trendex.trendex.domain.macd.binancemacd.service.BinanceMacdService;
import com.trendex.trendex.domain.rsi.binancersi.model.BinanceRsi;
import com.trendex.trendex.domain.rsi.binancersi.service.BinanceRsiService;
import com.trendex.trendex.global.client.webclient.service.TelegramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BinanceCandleFacade {

    private final BinanceCandleService binanceCandleService;

    private final BinanceSymbolService binanceSymbolService;

    private final BinanceCandleFetchService binanceCandleFetchService;

    private final BinanceMacdService binanceMacdService;

    private final TelegramService telegramService;

    private final BinanceRsiService binanceRsiService;

    //    @Scheduled(cron = "0 */1 * * * *")
    @Scheduled(fixedRate = 6000000)
    public void fetchAndSaveBinanceData() {
        List<BinanceSymbol> binanceSymbols = binanceSymbolService.findAll();


        Flux<BinanceCandle> binanceCandleFlux = binanceCandleFetchService.fetchBinanceData(binanceSymbols).share();
        binanceCandleService.saveAll(binanceCandleFlux)
                .then(Mono.zip(
                        Mono.fromRunnable(() -> saveRsi(binanceSymbols))
                                .subscribeOn(Schedulers.boundedElastic()),
                        Mono.fromRunnable(() -> saveMacd(binanceSymbols))
                                .subscribeOn(Schedulers.boundedElastic())
                ));


    }

    public void saveMacd(List<BinanceSymbol> binanceSymbols) {

        long startTime = System.currentTimeMillis();

        List<BinanceMacd> binanceMacds = binanceSymbols
                .stream()
                .map(binanceSymbol -> {
                    String symbol = binanceSymbol.getSymbol();
                    List<CryptoClosePrice> cryptoClosePrices26 = binanceCandleService.getClosePricesBySymbolAndTime(symbol, CandleAnalysisTime.MACD_TWENTY_SIX_TIME_STAMP.getTime());
                    List<Double> cryptoClosePrices26Values = cryptoClosePrices26
                            .stream()
                            .map(CryptoClosePrice::getClosePrice)
                            .collect(Collectors.toList());

                    List<Double> cryptoClosePrices12Values = cryptoClosePrices26.stream()
                            .filter(cryptoClosePrice -> cryptoClosePrice.getTimeStamp() <= CandleAnalysisTime.MACD_TWELVE_TIME_STAMP.getTime())
                            .map(CryptoClosePrice::getClosePrice)
                            .toList();

                    if (cryptoClosePrices26.size() < 26) {
                        return null;
                    }
                    Double macdValue = CandleAnalysisUtil.calculateMACD(cryptoClosePrices26Values, cryptoClosePrices12Values);
                    List<Double> macdValues = binanceMacdService.findAllBySymbolAndTimeStamp(symbol, CandleAnalysisTime.MACD_NINE_TIME_STAMP.getTime());
                    Double macdSignalValue = null;
                    if (macdValues.size() >= 9) {
                        macdSignalValue = CandleAnalysisUtil.calculateMACDSignal(macdValues);
                    }
                    return new BinanceMacd(symbol, macdValue, macdSignalValue, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        binanceMacdService.saveAll(binanceMacds);
    }

    public void saveRsi(List<BinanceSymbol> binanceSymbols) {
        List<BinanceRsi> binanceRsis = binanceSymbols
                .stream()
                .map(binanceSymbol -> {
                    String symbol = binanceSymbol.getSymbol();
                    List<Double> cryptoClosePrices14 = binanceCandleService.getClosePricesBySymbolAndTime(symbol, CandleAnalysisTime.RSI_FOURTEEN_TIME_STAMP.getTime())
                            .stream()
                            .map(CryptoClosePrice::getClosePrice)
                            .collect(Collectors.toList());
                    if (cryptoClosePrices14.size() < 14) {
                        return null;
                    }
                    Double rsiValue = CandleAnalysisUtil.calculateRSI(cryptoClosePrices14);
                    return new BinanceRsi(symbol, rsiValue, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        binanceRsiService.saveAll(binanceRsis);


    }

}
