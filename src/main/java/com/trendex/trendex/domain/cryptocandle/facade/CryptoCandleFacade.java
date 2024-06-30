package com.trendex.trendex.domain.cryptocandle.facade;

import com.trendex.trendex.domain.binancesymbol.model.BinanceSymbol;
import com.trendex.trendex.domain.binancesymbol.repository.BinanceSymbolRepository;
import com.trendex.trendex.domain.bithumbsymbol.model.BithumbSymbol;
import com.trendex.trendex.domain.bithumbsymbol.repository.BithumbSymbolRepository;
import com.trendex.trendex.domain.coinonesymbol.model.CoinoneSymbol;
import com.trendex.trendex.domain.coinonesymbol.repository.CoinoneSymbolRepository;
import com.trendex.trendex.domain.cryptocandle.model.CryptoCandle;
import com.trendex.trendex.domain.cryptocandle.service.CryptoCandleFetchService;
import com.trendex.trendex.domain.cryptocandle.service.CryptoCandleService;
import com.trendex.trendex.domain.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.domain.upbitsymbol.repository.UpbitSymbolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoCandleFacade {

    private final CryptoCandleService cryptoCandleService;

    private final CryptoCandleFetchService cryptoCandleFetchService;

    private final BinanceSymbolRepository binanceSymbolRepository;

    private final CoinoneSymbolRepository coinoneSymbolRepository;

    private final BithumbSymbolRepository bithumbSymbolRepository;

    private final UpbitSymbolRepository upbitSymbolRepository;

    public void fetchAndSaveBinanceData() {
        List<BinanceSymbol> binanceSymbols = binanceSymbolRepository.findAll();
        List<CryptoCandle> cryptoCandles = cryptoCandleFetchService.fetchBinanceData(binanceSymbols);
        cryptoCandleService.saveAll(cryptoCandles);
    }

    public void fetchAndSaveBithumbData() {
        List<BithumbSymbol> bithumbSymbols = bithumbSymbolRepository.findAll();
        List<CryptoCandle> cryptoCandles = cryptoCandleFetchService.fetchBithumbData(bithumbSymbols);
        cryptoCandleService.saveAll(cryptoCandles);
    }

    public void fetchAndSaveCoinoneData() {
        List<CoinoneSymbol> coinoneSymbols = coinoneSymbolRepository.findAll();
        List<CryptoCandle> cryptoCandles = cryptoCandleFetchService.fetchCoinoneData(coinoneSymbols);
        cryptoCandleService.saveAll(cryptoCandles);
    }

    public void fetchAndSaveUpbitData() {
        List<UpbitSymbol> upbitSymbols = upbitSymbolRepository.findAll();
        List<CryptoCandle> cryptoCandles = cryptoCandleFetchService.fetchUpbitData(upbitSymbols);
        cryptoCandleService.saveAll(cryptoCandles);
    }

}
