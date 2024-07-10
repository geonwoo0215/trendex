package com.trendex.trendex.domain.candle.upbitcandle.service;

import com.trendex.trendex.domain.candle.Decision;
import com.trendex.trendex.domain.candle.upbitcandle.model.UpbitCandle;
import com.trendex.trendex.domain.symbol.upbitsymbol.model.UpbitSymbol;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitAccountResponse;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitCandleResponse;
import com.trendex.trendex.global.client.webclient.dto.upbit.UpbitOrderResponse;
import com.trendex.trendex.global.client.webclient.service.UpbitWebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitCandleFetchService {

    private final UpbitWebClientService upbitWebClientService;

    public Flux<UpbitCandle> fetchUpbitData(List<UpbitSymbol> upbitSymbols) {

        return Flux.fromIterable(upbitSymbols)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(upbitSymbol ->
                        upbitWebClientService.getMinuteCandle(1, upbitSymbol.getSymbol(), 1)
                                .flatMapMany(Flux::fromIterable)
                                .map(UpbitCandleResponse::toUpbitCandle)
                )
                .sequential();

    }

    public List<UpbitAccountResponse> fetchKrwVolume() {
        return upbitWebClientService.getAccounts().block();
    }

    public Mono<UpbitOrderResponse> trade(String market, String symbol, Decision decision, List<UpbitAccountResponse> upbitAccountResponses) {
        if (decision == Decision.SELL) {
            Optional<String> symbolBalance = upbitAccountResponses.stream()
                    .filter(account -> symbol.equals(account.getCurrency()))
                    .map(UpbitAccountResponse::getBalance)
                    .findFirst();

            if (symbolBalance.isPresent()) {
                String balance = symbolBalance.get();
                log.info("ask");
                return upbitWebClientService.getOrders(market, "ask", balance, null, "best", "ioc");
            } else {
                return Mono.empty();
            }
        } else if (decision == Decision.BUY) {
            Optional<String> krwBalance = upbitAccountResponses.stream()
                    .filter(account -> "KRW".equals(account.getCurrency()))
                    .map(UpbitAccountResponse::getBalance)
                    .findFirst();

            if (krwBalance.isPresent()) {
                String balance = krwBalance.get();
                log.info("balance = {}", balance);
                double v = Double.parseDouble(balance) * 0.5;
                balance = String.valueOf(v);
                return upbitWebClientService.getOrders(market, "bid", null, balance, "best", "ioc");
            } else {
                return Mono.empty();
            }
        }
        return Mono.empty();
    }


}
