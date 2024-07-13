package com.trendex.trendex.domain.candle.upbitcandle.facade;

import com.trendex.trendex.domain.candle.CandleAnalysisTime;
import com.trendex.trendex.domain.candle.CandleAnalysisUtil;
import com.trendex.trendex.domain.candle.Decision;
import com.trendex.trendex.domain.candle.upbitcandle.model.UpbitCandle;
import com.trendex.trendex.domain.candle.upbitcandle.service.UpbitCandleFetchService;
import com.trendex.trendex.domain.candle.upbitcandle.service.UpbitCandleService;
import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacd;
import com.trendex.trendex.domain.macd.upbitmacd.service.UpbitMacdService;
import com.trendex.trendex.domain.symbol.upbitmarket.model.UpbitMarket;
import com.trendex.trendex.domain.symbol.upbitmarket.service.UpbitMarketService;
import com.trendex.trendex.global.client.webclient.service.TelegramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Scheduled(cron = "0 */1 * * * *")
    public void fetchSaveAndAnalyzeUpbitData() {
        List<UpbitMarket> upbitMarkets = upbitMarketService.findAll();
        Flux<UpbitCandle> upbitCandlesFlux = upbitCandleFetchService.fetchUpbitData(upbitMarkets).share();
        saveAll(upbitCandlesFlux)
                .then(Mono.fromRunnable(() -> saveMacd(upbitMarkets)));
        volumeAnalyze(upbitCandlesFlux);
    }

    private Flux<Void> saveAll(Flux<UpbitCandle> upbitCandlesFlux) {
        return upbitCandleService.saveAll(upbitCandlesFlux);
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

    private void volumeAnalyze(Flux<UpbitCandle> upbitCandlesFlux) {
        upbitCandlesFlux
                .flatMap(upbitCandle -> Mono.fromCallable(() -> upbitCandleService.getVolumesByMarketAndTime(upbitCandle.getMarket()))
                        .subscribeOn(Schedulers.boundedElastic())
                        .map(volumes -> CandleAnalysisUtil.isVolumeSpike(volumes, Double.parseDouble(upbitCandle.getVolume())))
                        .filter(isSpike -> isSpike)
                        .flatMap(isSpike -> telegramService.sendVolumeSpike()))
                .subscribe();
    }


    public Decision decideByMacd(String market) {
        UpbitMacd upbitMacd = upbitMacdService.findLatestMacd(market);
        return CandleAnalysisUtil.decideByMacd(upbitMacd.getMacdValue(), upbitMacd.getMacdSignalValue(), upbitMacd.isSignalHigherThanMacd());
    }

    public Decision decideByRsi(String market) {
        List<Double> cryptoClosePrices14 = upbitCandleService.getClosePricesByMarketAndTime(market, CandleAnalysisTime.RSI_FOURTEEN_TIME_STAMP.getTime());
        return CandleAnalysisUtil.calculateRSI(cryptoClosePrices14);
    }


}
