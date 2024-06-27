package com.trendex.trendex.domain.cryptocandle.service;

import com.trendex.trendex.domain.cryptocandle.model.CryptoCandle;
import com.trendex.trendex.domain.cryptocandle.repository.CryptoCandleRepository;
import com.trendex.trendex.global.client.webclient.dto.binance.BinanceCandle;
import com.trendex.trendex.global.client.webclient.dto.binance.BinanceTickerPrice;
import com.trendex.trendex.global.client.webclient.dto.bithumb.BithumbCandle;
import com.trendex.trendex.global.client.webclient.dto.bithumb.BithumbWithdrawMinimum;
import com.trendex.trendex.global.client.webclient.dto.coinone.CoinoneCandle;
import com.trendex.trendex.global.client.webclient.dto.coinone.CoinoneCandleData;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitCandleData;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitMarketCode;
import com.trendex.trendex.global.client.webclient.service.BinanceWebClientService;
import com.trendex.trendex.global.client.webclient.service.BithumbWebClientService;
import com.trendex.trendex.global.client.webclient.service.CoinoneWebClientService;
import com.trendex.trendex.global.client.webclient.service.UpbitWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void fetchBinanceData() {
        List<CryptoCandle> cryptoCandles = binanceWebClientService.getTickerPrice()
                .stream()
                .flatMap(binanceTickerPrice -> {
                    String symbol = binanceTickerPrice.getSymbol();
                    return binanceWebClientService.getCandle(symbol, "1m")
                            .stream()
                            .map(binanceCandle -> binanceCandle.toCryptoCandle(symbol));
                })
                .collect(Collectors.toList());

        cryptoCandleRepository.saveAll(cryptoCandles);
    }

    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void fetchBithumbData() {

        List<CryptoCandle> cryptoCandles = bithumbWebClientService.getWithdrawMinimum("KRW")
                .getData().stream()
                .flatMap(bithumbCurrencyData -> bithumbWebClientService.getCandle(bithumbCurrencyData.getCurrency(), "KRW", "1m")
                        .toCryptoCandleList(bithumbCurrencyData.getCurrency()).stream())
                .collect(Collectors.toList());

        cryptoCandleRepository.saveAll(cryptoCandles);
    }

    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void fetchCoinoneData() {
        CoinoneCandle candle = coinoneWebClientService.getCandle("KRW", "BTC", "1m", 200);
        List<CryptoCandle> btc = candle.getChart().stream()
                .map(a -> a.toCryptoCandle("BTC"))
                .collect(Collectors.toList());
        cryptoCandleRepository.saveAll(btc);
    }

    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void fetchUpbitData() {

        List<CryptoCandle> cryptoCandles = upbitWebClientService.getMarketCode().stream()
                .flatMap(upbitMarketCode -> upbitWebClientService.getMinuteCandle(1, upbitMarketCode.getMarket(), 200)
                        .stream()
                        .map(upbitCandleData -> upbitCandleData.toCryptoCandle(upbitMarketCode.getMarket())))
                .collect(Collectors.toList());

        cryptoCandleRepository.saveAll(cryptoCandles);
    }

}
