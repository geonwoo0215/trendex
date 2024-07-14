package com.trendex.trendex.domain.macd.upbitmacd.controller;

import com.trendex.trendex.domain.macd.upbitmacd.dto.UpbitMacdResponse;
import com.trendex.trendex.domain.macd.upbitmacd.facade.UpbitMacdFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UpbitMacdController {

    private final UpbitMacdFacade upbitMacdFacade;

    @GetMapping(value = "/macds", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UpbitMacdResponse>> findAll() {
        List<UpbitMacdResponse> response = upbitMacdFacade.findAllMacds();
        return ResponseEntity.ok(response);
    }

}
