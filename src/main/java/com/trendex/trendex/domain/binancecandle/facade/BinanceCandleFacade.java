package com.trendex.trendex.domain.binancecandle.facade;

import com.trendex.trendex.domain.binancecandle.model.BinanceCandle;
import com.trendex.trendex.domain.binancecandle.service.BinanceCandleFetchService;
import com.trendex.trendex.domain.binancecandle.service.BinanceCandleService;
import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.binancesymbol.service.BinanceSymbolService;
import com.trendex.trendex.domain.candle.CandleAnalysisTime;
import com.trendex.trendex.domain.candle.CandleAnalysisUtil;
import com.trendex.trendex.domain.macd.binancemacd.service.BinanceMacdService;
import com.trendex.trendex.domain.rsi.binancersi.service.BinanceRsiService;
import com.trendex.trendex.global.client.webclient.service.TelegramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    //    @Scheduled(cron = "0 */3 * * * *")
    public Mono<Void> fetchAndSaveBinanceData() {
        List<BinanceSymbol> binanceSymbols = binanceSymbolService.findAll();

        Flux<BinanceCandle> binanceCandleFlux = binanceCandleFetchService.fetchBinanceData(binanceSymbols).share();

        return binanceCandleService.saveAll(binanceCandleFlux)
                .then(Mono.zip(
                        Mono.fromRunnable(() -> saveRsi(binanceSymbols)),
                        Mono.fromRunnable(() -> saveMacd(binanceSymbols))
                )).then();


    }

    public void saveMacd(List<BinanceSymbol> binanceSymbols) {
        binanceSymbols
                .forEach(binanceSymbol -> {
                    String symbol = binanceSymbol.getSymbol();
                    List<Double> cryptoClosePrices26 = binanceCandleService.getClosePricesBySymbolAndTime(symbol, CandleAnalysisTime.MACD_TWENTY_SIX_TIME_STAMP.getTime());
                    List<Double> cryptoClosePrices12 = binanceCandleService.getClosePricesBySymbolAndTime(symbol, CandleAnalysisTime.MACD_TWELVE_TIME_STAMP.getTime());
                    if (cryptoClosePrices26.size() < 26 || cryptoClosePrices12.size() < 12) {
                        return;
                    }
                    Double macdValue = CandleAnalysisUtil.calculateMACD(cryptoClosePrices26, cryptoClosePrices12);
                    List<Double> macdValues = binanceMacdService.findAllBySymbolAndTimeStamp(symbol, CandleAnalysisTime.MACD_NINE_TIME_STAMP.getTime());
                    Double macdSignalValue = null;
                    if (macdValues.size() >= 9) {
                        macdSignalValue = CandleAnalysisUtil.calculateMACDSignal(macdValues);
                    }
                    binanceMacdService.save(symbol, macdValue, macdSignalValue);
                });
    }

    public void saveRsi(List<BinanceSymbol> binanceSymbols) {
        binanceSymbols
                .forEach(binanceSymbol -> {
                    String symbol = binanceSymbol.getSymbol();
                    List<Double> cryptoClosePrices14 = binanceCandleService.getClosePricesBySymbolAndTime(symbol, CandleAnalysisTime.RSI_FOURTEEN_TIME_STAMP.getTime());
                    Double rsiValue = CandleAnalysisUtil.calculateRSI(cryptoClosePrices14);
                    binanceRsiService.save(symbol, rsiValue);
                });
    }

}
