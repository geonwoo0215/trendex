package com.trendex.trendex.domain.rsi.upbitrsi.controller;

import com.trendex.trendex.domain.rsi.upbitrsi.dto.UpbitRsiResponse;
import com.trendex.trendex.domain.rsi.upbitrsi.facade.UpbitRsiFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UpbitRsiController {

    private final UpbitRsiFacade upbitRsiFacade;

    @GetMapping(value = "/rsis", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UpbitRsiResponse>> findAll() {
        List<UpbitRsiResponse> response = upbitRsiFacade.findAllRsis();
        return ResponseEntity.ok(response);
    }

}
