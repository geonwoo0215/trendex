package com.trendex.trendex.domain.rsi.binancersi.controller;

import com.trendex.trendex.domain.rsi.binancersi.facade.BinanceRsiFacade;
import com.trendex.trendex.domain.rsi.dto.RsiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "바이낸스 rsi", description = "바이낸스 rsi 조회 api")
@RestController
@RequiredArgsConstructor
public class BinanceRsiController {

    private final BinanceRsiFacade binanceRsiFacade;

    @Operation(
            summary = "바이낸스 rsi 조회 api",
            description = "바이낸스 rsi 반환",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "바이낸스 rsi 값 반환",
                            content = @Content(schema = @Schema(implementation = RsiResponse.class))
                    )
            }
    )
    @GetMapping(value = "/rsis/binance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RsiResponse>> findAll() {
        List<RsiResponse> response = binanceRsiFacade.findAllRsis();
        return ResponseEntity.ok(response);
    }

}
