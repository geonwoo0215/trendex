package com.trendex.trendex.domain.rsi.upbitrsi.service;

import com.trendex.trendex.domain.rsi.upbitrsi.model.UpbitRsi;
import com.trendex.trendex.domain.rsi.upbitrsi.repository.UpbitRsiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitRsiService {

    private final UpbitRsiRepository upbitRsiRepository;

    @Transactional
    public void save(String market, Double value) {
        UpbitRsi upbitRsi = new UpbitRsi(market, value);
        upbitRsiRepository.save(upbitRsi);
    }

    @Transactional(readOnly = true)
    public UpbitRsi findLatest(String market) {
        return upbitRsiRepository.findLatest(market)
                .orElseThrow(() -> new RuntimeException());

    }

    @Transactional(readOnly = true)
    public List<UpbitRsi> findLatest(List<String> markets) {
        return upbitRsiRepository.findLatestForMarkets(markets);
    }


}
