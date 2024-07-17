package com.trendex.trendex.domain.rsi.upbitrsi.controller;

import com.trendex.trendex.domain.rsi.upbitrsi.dto.UpbitRsiResponse;
import com.trendex.trendex.domain.rsi.upbitrsi.facade.UpbitRsiFacade;
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

@Tag(name = "rsi", description = "rsi 조회 api")
@RestController
@RequiredArgsConstructor
public class UpbitRsiController {

    private final UpbitRsiFacade upbitRsiFacade;

    @Operation(
            summary = "rsi 조회 api",
            description = "rsi 반환",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "모든 rsi 값 반환",
                            content = @Content(schema = @Schema(implementation = UpbitRsiResponse.class))
                    )
            }
    )
    @GetMapping(value = "/rsis", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UpbitRsiResponse>> findAll() {
        List<UpbitRsiResponse> response = upbitRsiFacade.findAllRsis();
        return ResponseEntity.ok(response);
    }

}
