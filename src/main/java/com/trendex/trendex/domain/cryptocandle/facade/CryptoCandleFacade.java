package com.trendex.trendex.domain.cryptocandle.facade;

import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.binancesymbol.service.BinanceSymbolService;
import com.trendex.trendex.domain.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.domain.bithumbsymbol.service.BithumbSymbolService;
import com.trendex.trendex.domain.coinonesymbol.model.CoinoneSymbol;
import com.trendex.trendex.domain.coinonesymbol.service.CoinoneSymbolService;
import com.trendex.trendex.domain.cryptocandle.model.CryptoCandle;
import com.trendex.trendex.domain.cryptocandle.service.CryptoCandleFetchService;
import com.trendex.trendex.domain.cryptocandle.service.CryptoCandleService;
import com.trendex.trendex.domain.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.domain.upbitsymbol.service.UpbitSymbolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CryptoCandleFacade {

    private final CryptoCandleService cryptoCandleService;

    private final CryptoCandleFetchService cryptoCandleFetchService;

    private final BinanceSymbolService binanceSymbolService;

    private final CoinoneSymbolService coinoneSymbolService;

    private final BithumbSymbolService bithumbSymbolService;

    private final UpbitSymbolService upbitSymbolService;

    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveBinanceData() {
        List<BinanceSymbol> binanceSymbols = binanceSymbolService.findAll();
        List<CryptoCandle> cryptoCandles = cryptoCandleFetchService.fetchBinanceData(binanceSymbols);
        cryptoCandleService.saveAll(cryptoCandles);
        log.info("binance fetch done");

    }

    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveBithumbData() {
        List<BithumbSymbol> bithumbSymbols = bithumbSymbolService.findAll();
        List<CryptoCandle> cryptoCandles = cryptoCandleFetchService.fetchBithumbData(bithumbSymbols);
        cryptoCandleService.saveAll(cryptoCandles);
        log.info("bithumb fetch done");
    }

    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveCoinoneData() {
        List<CoinoneSymbol> coinoneSymbols = coinoneSymbolService.findAll();
        List<CryptoCandle> cryptoCandles = cryptoCandleFetchService.fetchCoinoneData(coinoneSymbols);
        cryptoCandleService.saveAll(cryptoCandles);
        log.info("coinone fetch done");
    }

    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveUpbitData() {
        List<UpbitSymbol> upbitSymbols = upbitSymbolService.findAll();
        List<CryptoCandle> cryptoCandles = cryptoCandleFetchService.fetchUpbitData(upbitSymbols);
        cryptoCandleService.saveAll(cryptoCandles);
        log.info("upbit fetch done");
    }

}
