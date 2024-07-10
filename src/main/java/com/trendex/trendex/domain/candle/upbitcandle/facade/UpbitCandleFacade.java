package com.trendex.trendex.domain.candle.upbitcandle.facade;

import com.trendex.trendex.domain.candle.CandleAnalysisService;
import com.trendex.trendex.domain.candle.CandleAnalysisTime;
import com.trendex.trendex.domain.candle.Decision;
import com.trendex.trendex.domain.candle.upbitcandle.model.UpbitCandle;
import com.trendex.trendex.domain.candle.upbitcandle.service.UpbitCandleFetchService;
import com.trendex.trendex.domain.candle.upbitcandle.service.UpbitCandleService;
import com.trendex.trendex.domain.macd.upbitmacd.model.UpbitMacd;
import com.trendex.trendex.domain.macd.upbitmacd.service.UpbitMacdService;
import com.trendex.trendex.domain.macd.upbitmacd.service.UpbitMacdSignalService;
import com.trendex.trendex.domain.order.model.Order;
import com.trendex.trendex.domain.order.repository.OrderRepository;
import com.trendex.trendex.domain.symbol.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.domain.symbol.upbitsymbol.service.UpbitSymbolService;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitAccountResponse;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitCandleFacade {

    private final UpbitCandleService upbitCandleService;

    private final UpbitCandleFetchService upbitCandleFetchService;

    private final UpbitSymbolService upbitSymbolService;

    private final CandleAnalysisService candleAnalysisService;

    private final UpbitMacdService upbitMacdService;

    private final UpbitMacdSignalService upbitMacdSignalService;

    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 */1 * * * *")
    public void fetchSaveAndAnalyzeUpbitData() {
//        List<UpbitSymbol> upbitSymbols = upbitSymbolService.findAll();
        List<UpbitSymbol> upbitSymbols = List.of(new UpbitSymbol("KRW-HPO"));
        Flux<UpbitCandle> upbitCandlesFlux = upbitCandleFetchService.fetchUpbitData(upbitSymbols).share();

        saveUpbitCandles(upbitCandlesFlux);
//        analyzeUpbitCandles(upbitCandlesFlux);
    }

    private void saveUpbitCandles(Flux<UpbitCandle> upbitCandlesFlux) {
        upbitCandlesFlux
                .buffer(100)
                .flatMap(upbitCandles -> Mono.fromFuture(CompletableFuture.runAsync(() -> upbitCandleService.saveAll(upbitCandles))))
                .subscribe();
    }

    private void analyzeUpbitCandles(Flux<UpbitCandle> upbitCandlesFlux) {
        upbitCandlesFlux
                .flatMap(upbitCandle ->
                        Mono.fromFuture(CompletableFuture.supplyAsync(() ->
                                        upbitCandleService.getCandlesByMarketAndTime(upbitCandle.getMarket())))
                                .map(upbitCandleMappings ->
                                        candleAnalysisService.isVolumeSpike(upbitCandleMappings, Double.parseDouble(upbitCandle.getVolume()))))
                .subscribe();
    }

    @Scheduled(fixedRate = 60000)
    public void autoTrading() {

        String market = "KRW-HPO";
        String symbol = "HPO";

        List<UpbitAccountResponse> listMono = upbitCandleFetchService.fetchKrwVolume();
        listMono.stream()
                .forEach(upbitAccountResponse -> {
                    log.info("{}", upbitAccountResponse.getCurrency());
                    log.info("{}", upbitAccountResponse.getBalance());
                });
        Decision decision = decideByMacd(market);
        UpbitOrderResponse trade = upbitCandleFetchService.trade(market, symbol, decision, listMono).block();
        try {
            Order order = trade.toOrder();
            orderRepository.save(order);
        } catch (Exception e) {
            log.info("no trade");
        }


    }


    public Decision decideByMacd(String symbol) {
        List<Double> cryptoClosePrices26 = upbitCandleService.getClosePricesBySymbolAndTime(symbol, CandleAnalysisTime.MACD_TWENTY_SIX_TIME_STAMP.getTime());
        List<Double> cryptoClosePrices12 = upbitCandleService.getClosePricesBySymbolAndTime(symbol, CandleAnalysisTime.MACD_TWELVE_TIME_STAMP.getTime());
        if (cryptoClosePrices26.size() != 0 && cryptoClosePrices12.size() != 0) {
            double macd = candleAnalysisService.calculateMACD(cryptoClosePrices26, cryptoClosePrices12);
            UpbitMacd save = upbitMacdService.save(symbol, macd);
            List<Double> macdValues = upbitMacdService.findAllBySymbolAndTimeStamp(symbol, CandleAnalysisTime.MACD_NINE_TIME_STAMP.getTime());
            double macdSignal = candleAnalysisService.calculateMACDSignal(macdValues);
            upbitMacdService.signalSave(macdSignal, save);
            boolean latestSignalIsHigherThanMacd = upbitMacdSignalService.findLatestSignalIsHigherThanMacd();
            return candleAnalysisService.decideByMacd(macd, macdSignal, latestSignalIsHigherThanMacd);
        }
        return Decision.NOTHING;
    }


}
