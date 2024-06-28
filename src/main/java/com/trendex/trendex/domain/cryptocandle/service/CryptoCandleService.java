package com.trendex.trendex.domain.cryptocandle.service;

import com.trendex.trendex.domain.cryptocandle.model.CryptoCandle;
import com.trendex.trendex.domain.cryptocandle.repository.CryptoCandleRepository;
import com.trendex.trendex.global.client.webclient.service.BinanceWebClientService;
import com.trendex.trendex.global.client.webclient.service.BithumbWebClientService;
import com.trendex.trendex.global.client.webclient.service.CoinoneWebClientService;
import com.trendex.trendex.global.client.webclient.service.UpbitWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CryptoCandleService {

    private final CryptoCandleRepository cryptoCandleRepository;

    private final BinanceWebClientService binanceWebClientService;

    private final BithumbWebClientService bithumbWebClientService;

    private final CoinoneWebClientService coinoneWebClientService;

    private final UpbitWebClientService upbitWebClientService;

    @Scheduled(cron = "0 */1 * * * *")
    public void fetchBinanceCandleData() {
        getBinanceCandleData().subscribe(this::saveAll, error -> {
            System.err.println("Error occurred while fetching Binance data: " + error.getMessage());
        });
    }

    public Mono<List<CryptoCandle>> getBinanceCandleData() {

        return binanceWebClientService.getTickerPrice()
                .flatMapMany(Flux::fromIterable)
                .flatMap(binanceTickerPrice ->
                        binanceWebClientService.getCandle(binanceTickerPrice.getSymbol(), "3m", 200)
                                .flatMapMany(Flux::fromIterable)
                                .map(binanceCandle -> binanceCandle.toCryptoCandle(binanceTickerPrice.getSymbol()))
                )
                .collectList();
    }

    @Transactional
    public void saveAll(List<CryptoCandle> cryptoCandles) {
        cryptoCandleRepository.saveAll(cryptoCandles);
    }

    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void fetchBithumbData() {

        List<CryptoCandle> cryptoCandles = bithumbWebClientService.getWithdrawMinimum("KRW")
                .getData()
                .stream()
                .flatMap(bithumbCurrencyData -> bithumbWebClientService.getCandle(bithumbCurrencyData.getCurrency(), "KRW", "3m")
                        .toCryptoCandleList(bithumbCurrencyData.getCurrency()).stream())
                .collect(Collectors.toList());

        cryptoCandleRepository.saveAll(cryptoCandles);
    }

    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void fetchCoinoneData() {

        List<CryptoCandle> cryptoCandles = coinoneWebClientService.getAllCurrencies()
                .getCurrencies()
                .stream()
                .flatMap(coinoneCurrencyDetail -> coinoneWebClientService.getCandle("KRW", coinoneCurrencyDetail.getSymbol(), "3m", 200)
                        .getChart().stream()
                        .map(coinoneCandleData -> coinoneCandleData.toCryptoCandle(coinoneCurrencyDetail.getSymbol())))
                .collect(Collectors.toList());

        cryptoCandleRepository.saveAll(cryptoCandles);
    }

    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void fetchUpbitData() {

        List<CryptoCandle> cryptoCandles = upbitWebClientService.getMarketCode()
                .stream()
                .flatMap(upbitMarketCode -> upbitWebClientService.getMinuteCandle(3, upbitMarketCode.getMarket(), 200)
                        .stream()
                        .map(upbitCandleData -> upbitCandleData.toCryptoCandle(upbitMarketCode.getMarket())))
                .collect(Collectors.toList());

        cryptoCandleRepository.saveAll(cryptoCandles);
    }

}
