package com.trendex.trendex.domain.macd.upbitmacd.controller;

import com.trendex.trendex.domain.macd.upbitmacd.dto.UpbitMacdResponse;
import com.trendex.trendex.domain.macd.upbitmacd.facade.UpbitMacdFacade;
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

@Tag(name = "macd", description = "macd 조회 api")
@RestController
@RequiredArgsConstructor
public class UpbitMacdController {

    private final UpbitMacdFacade upbitMacdFacade;

    @Operation(
            summary = "macd 조회 api",
            description = "macd 반환",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "모든 macd 값 반환",
                            content = @Content(schema = @Schema(implementation = UpbitMacdResponse.class))
                    )
            }
    )
    @GetMapping(value = "/macds", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UpbitMacdResponse>> findAll() {
        List<UpbitMacdResponse> response = upbitMacdFacade.findAllMacds();
        return ResponseEntity.ok(response);
    }

}
