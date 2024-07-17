package com.trendex.trendex.domain.binancecandle.facade;

import com.trendex.trendex.domain.binancecandle.model.BinanceCandle;
import com.trendex.trendex.domain.binancecandle.service.BinanceCandleFetchService;
import com.trendex.trendex.domain.binancecandle.service.BinanceCandleService;
import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.binancesymbol.service.BinanceSymbolService;
import com.trendex.trendex.domain.candle.CandleAnalysisUtil;
import com.trendex.trendex.global.client.webclient.service.TelegramWebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BinanceCandleFacade {

    private final BinanceCandleService binanceCandleService;

    private final BinanceSymbolService binanceSymbolService;

    private final BinanceCandleFetchService binanceCandleFetchService;

    private final TelegramWebClientService telegramWebClientService;

    //    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveBinanceData() {
        List<BinanceSymbol> binanceSymbols = binanceSymbolService.findAll();

        Flux<BinanceCandle> binanceCandleFlux = binanceCandleFetchService.fetchBinanceData(binanceSymbols).share();

        binanceCandleFlux
                .buffer(1000)
                .flatMap(binanceCandles -> Mono.fromFuture(CompletableFuture.runAsync(() -> binanceCandleService.saveAll(binanceCandles))))
                .subscribe();

        binanceCandleFlux
                .flatMap(binanceCandle ->
                        Mono.fromFuture(() ->
                                        CompletableFuture.supplyAsync(() ->
                                                binanceCandleService.getCandlesBySymbolAndTime(binanceCandle.getSymbol())
                                        )
                                )
                                .flatMap(binanceCandleMappings ->
                                        Mono.defer(() -> {
                                            double volume = Double.parseDouble(binanceCandle.getVolume());
                                            List<Double> list = binanceCandleMappings.stream()
                                                    .map(cryptoVolume -> Double.parseDouble(cryptoVolume.getVolume()))
                                                    .collect(Collectors.toList());
                                            boolean volumeSpike = CandleAnalysisUtil.isVolumeSpike(list, volume);
                                            if (volumeSpike) {
                                                String text = binanceCandle.getSymbol() + "급등하였습니다.";
                                                return telegramWebClientService.sendMessage(text);
                                            } else {
                                                return Mono.empty();
                                            }
                                        })
                                )
                )
                .subscribe();
        log.info("binance fetch done");
    }

}
