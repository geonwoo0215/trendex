package com.trendex.trendex.domain.upbitcandle.facade;

import com.trendex.trendex.domain.candle.CandleAnalysisTime;
import com.trendex.trendex.domain.candle.CandleAnalysisUtil;
import com.trendex.trendex.domain.candle.CryptoClosePrice;
import com.trendex.trendex.domain.candle.CryptoClosePrices;
import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacd;
import com.trendex.trendex.domain.macd.upbitmacd.service.UpbitMacdService;
import com.trendex.trendex.domain.rsi.upbitrsi.model.UpbitRsi;
import com.trendex.trendex.domain.rsi.upbitrsi.service.UpbitRsiService;
import com.trendex.trendex.domain.upbitcandle.model.UpbitCandle;
import com.trendex.trendex.domain.upbitcandle.service.UpbitCandleFetchService;
import com.trendex.trendex.domain.upbitcandle.service.UpbitCandleService;
import com.trendex.trendex.domain.upbitmarket.service.UpbitMarketService;
import com.trendex.trendex.global.client.webclient.service.TelegramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Scheduled(cron = "0 */1 * * * *")
    public void fetchSaveAndAnalyzeUpbitData() {

        List<String> upbitMarkets = upbitMarketService.findMarketsStartWithKRW();
        Flux<UpbitCandle> upbitCandlesFlux = upbitCandleFetchService.fetchUpbitData(upbitMarkets);

        upbitCandleService.saveAll(upbitCandlesFlux)
                .then(Mono.zip(
                        Mono.fromRunnable(() -> saveRsi(upbitMarkets))
                                .subscribeOn(Schedulers.boundedElastic()),
                        Mono.fromRunnable(() -> saveMacd(upbitMarkets))
                                .subscribeOn(Schedulers.boundedElastic())
                ))
                .subscribe();
    }

    public void saveMacd(List<String> upbitMarkets) {
        List<UpbitMacd> upbitMacds = upbitMarkets.stream()
                .map(upbitMarket -> {
                    String market = upbitMarket;
                    List<CryptoClosePrice> closePriceList26 = upbitCandleService.getClosePricesByMarketAndTime(market, CandleAnalysisTime.MACD_TWENTY_SIX_TIME_STAMP.getTime());
                    CryptoClosePrices cryptoClosePrices26 = new CryptoClosePrices(closePriceList26);

                    List<CryptoClosePrice> closePriceList12 = cryptoClosePrices26.getClosePriceValues(CandleAnalysisTime.MACD_TWELVE_TIME_STAMP);
                    CryptoClosePrices cryptoClosePrices12 = new CryptoClosePrices(closePriceList12);

                    List<Double> macdValues = upbitMacdService.findAllBySymbolAndTimeStamp(market, CandleAnalysisTime.MACD_NINE_TIME_STAMP.getTime());

                    List<Double> closePriceValues26 = cryptoClosePrices26.getClosePriceValues();
                    List<Double> closePriceValues12 = cryptoClosePrices12.getClosePriceValues();

                    return UpbitMacd.of(upbitMarket, closePriceValues26, closePriceValues12, macdValues);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        upbitMacdService.saveAll(upbitMacds);
    }

    public void saveRsi(List<String> upbitMarkets) {
        List<UpbitRsi> upbitRsis = upbitMarkets
                .parallelStream()
                .map(upbitMarket -> {
                    List<CryptoClosePrice> closePriceList = upbitCandleService.getClosePricesByMarketAndTime(upbitMarket, CandleAnalysisTime.RSI_FOURTEEN_TIME_STAMP.getTime());
                    CryptoClosePrices cryptoClosePrices = new CryptoClosePrices(closePriceList);
                    return UpbitRsi.of(upbitMarket, cryptoClosePrices.getClosePriceValues());
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        upbitRsiService.saveAll(upbitRsis);
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
