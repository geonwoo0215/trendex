package com.trendex.trendex.domain.upbitcandle.facade;

import com.trendex.trendex.domain.candle.CandleAnalysisTime;
import com.trendex.trendex.domain.candle.CandleAnalysisUtil;
import com.trendex.trendex.domain.macd.upbitmacd.service.UpbitMacdService;
import com.trendex.trendex.domain.rsi.upbitrsi.service.UpbitRsiService;
import com.trendex.trendex.domain.upbitcandle.model.UpbitCandle;
import com.trendex.trendex.domain.upbitcandle.service.UpbitCandleFetchService;
import com.trendex.trendex.domain.upbitcandle.service.UpbitCandleService;
import com.trendex.trendex.domain.upbitmarket.model.UpbitMarket;
import com.trendex.trendex.domain.upbitmarket.service.UpbitMarketService;
import com.trendex.trendex.global.client.webclient.service.TelegramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitCandleFacade {

    private final UpbitCandleService upbitCandleService;

    private final UpbitCandleFetchService upbitCandleFetchService;

    private final UpbitMarketService upbitMarketService;

    private final UpbitMacdService upbitMacdService;

    private final TelegramService telegramService;

    private final UpbitRsiService upbitRsiService;

    //    @Scheduled(cron = "0 */1 * * * *")
    public Mono<Void> fetchSaveAndAnalyzeUpbitData() {
        List<UpbitMarket> upbitMarkets = upbitMarketService.findAll();
        Flux<UpbitCandle> upbitCandlesFlux = upbitCandleFetchService.fetchUpbitData(upbitMarkets).share();
        return upbitCandleService.saveAll(upbitCandlesFlux)
                .then(Mono.zip(
                        Mono.fromRunnable(() -> saveRsi(upbitMarkets)),
                        Mono.fromRunnable(() -> saveMacd(upbitMarkets))
                )).then();
//                .subscribe();
//        volumeAnalyze(upbitCandlesFlux);
    }

    public void saveMacd(List<UpbitMarket> upbitMarkets) {
        upbitMarkets
                .forEach(upbitMarket -> {
                    String market = upbitMarket.getMarket();
                    List<Double> cryptoClosePrices26 = upbitCandleService.getClosePricesByMarketAndTime(market, CandleAnalysisTime.MACD_TWENTY_SIX_TIME_STAMP.getTime());
                    List<Double> cryptoClosePrices12 = upbitCandleService.getClosePricesByMarketAndTime(market, CandleAnalysisTime.MACD_TWELVE_TIME_STAMP.getTime());
                    if (cryptoClosePrices26.size() < 26 || cryptoClosePrices12.size() < 12) {
                        return;
                    }
                    Double macdValue = CandleAnalysisUtil.calculateMACD(cryptoClosePrices26, cryptoClosePrices12);
                    List<Double> macdValues = upbitMacdService.findAllBySymbolAndTimeStamp(market, CandleAnalysisTime.MACD_NINE_TIME_STAMP.getTime());
                    Double macdSignalValue = null;
                    if (macdValues.size() >= 9) {
                        macdSignalValue = CandleAnalysisUtil.calculateMACDSignal(macdValues);
                    }
                    upbitMacdService.save(market, macdValue, macdSignalValue);
                });
    }

    public void saveRsi(List<UpbitMarket> upbitMarkets) {
        upbitMarkets
                .forEach(upbitMarket -> {
                    String market = upbitMarket.getMarket();
                    List<Double> cryptoClosePrices14 = upbitCandleService.getClosePricesByMarketAndTime(market, CandleAnalysisTime.RSI_FOURTEEN_TIME_STAMP.getTime());
                    Double rsiValue = CandleAnalysisUtil.calculateRSI(cryptoClosePrices14);
                    upbitRsiService.save(market, rsiValue);
                });
    }

    private void volumeAnalyze(Flux<UpbitCandle> upbitCandlesFlux) {
        upbitCandlesFlux
                .flatMap(upbitCandle -> Mono.fromCallable(() -> upbitCandleService.getVolumesByMarketAndTime(upbitCandle.getMarket()))
                        .subscribeOn(Schedulers.boundedElastic())
                        .map(volumes -> CandleAnalysisUtil.isVolumeSpike(volumes, Double.parseDouble(upbitCandle.getVolume())))
                        .filter(isSpike -> isSpike)
                        .flatMap(isSpike -> telegramService.sendVolumeSpike()))
                .subscribe();
    }

}
