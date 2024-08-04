package com.trendex.trendex.domain.trade.upbittrade.controller;

import com.trendex.trendex.domain.rsi.dto.RsiResponse;
import com.trendex.trendex.domain.trade.upbittrade.dto.MarketAggregateDto;
import com.trendex.trendex.domain.trade.upbittrade.facade.UpbitTradeFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Tag(name = "업비트 거래조회", description = "업비트 거래조회")
@RestController
@RequiredArgsConstructor
public class UpbitTradeController {

    private final UpbitTradeFacade upbitTradeFacade;

    @Operation(
            summary = "업비트 거래, 호가 정보 조회",
            description = "업비트 거래, 호가 정보를 계산하여 시간당 반환",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "업비트 거래, 호가 정보를 계산하여 시간당 반환",
                            content = @Content(schema = @Schema(implementation = RsiResponse.class))
                    )
            }
    )

    @GetMapping(value = "/trades/upbit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MarketAggregateDto> findAggregatedMarketData
            (
                    @RequestParam(value = "market") String market,
                    @RequestParam(value = "startTime")
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime
            ) {

        Long startTimeStamp = startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();


        MarketAggregateDto response = upbitTradeFacade.findAggregatedMarketData(market, startTimeStamp);
        return ResponseEntity.ok(response);
    }


}
