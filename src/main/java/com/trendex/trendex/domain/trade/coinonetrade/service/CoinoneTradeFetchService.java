package com.trendex.trendex.domain.trade.coinonetrade.service;

import com.trendex.trendex.domain.symbol.coinonesymbol.model.CoinoneSymbol;
import com.trendex.trendex.domain.trade.coinonetrade.model.CoinoneTrade;
import com.trendex.trendex.global.client.webclient.service.CoinoneWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoinoneTradeFetchService {

    private final CoinoneWebClientService coinoneWebClientService;

    public List<CoinoneTrade> fetchCoinoneData(List<CoinoneSymbol> coinoneSymbols) {

        return Flux.fromIterable(coinoneSymbols)
                .flatMap(coinoneSymbol ->
                        coinoneWebClientService.getTransactionHistory("KRW", coinoneSymbol.getSymbol(), 200)
                                .flatMapMany(coinoneTransactionHistory ->
                                        Optional.ofNullable(coinoneTransactionHistory.getTransactions())
                                                .map(coinoneTransactionHistoryData -> Flux.fromIterable(coinoneTransactionHistoryData)
                                                        .map(transactionHistoryData -> transactionHistoryData.toCoinoneTrade(coinoneSymbol.getSymbol()))
                                                )
                                                .orElseGet(Flux::empty)
                                )
                )
                .collectList()
                .block();

    }

}
