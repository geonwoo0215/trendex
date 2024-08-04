package com.trendex.trendex.domain.orderbook.upbitorderbook.facade;

import com.trendex.trendex.domain.orderbook.upbitorderbook.model.UpbitOrderBook;
import com.trendex.trendex.domain.orderbook.upbitorderbook.service.UpbitOrderBookFetchService;
import com.trendex.trendex.domain.orderbook.upbitorderbook.service.UpbitOrderBookService;
import com.trendex.trendex.domain.upbitmarket.service.UpbitMarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitOrderBookFacade {

    private final UpbitOrderBookService upbitOrderBookService;

    private final UpbitOrderBookFetchService upbitOrderBookFetchService;

    private final UpbitMarketService upbitMarketService;

    @Scheduled(cron = "0 */3 * * * *")
    public void fetchAndSaveUpbitData() {
        List<String> upbitSymbols = upbitMarketService.findAll();

        Flux<UpbitOrderBook> upbitOrderBookFlux = upbitOrderBookFetchService.fetchUpbitData(upbitSymbols);
        upbitOrderBookService.saveAll(upbitOrderBookFlux)
                .subscribe();
    }

}
