package com.moneyplay.MoneyPlay.controller;

import com.moneyplay.MoneyPlay.domain.dto.StockAPITokenDto;
import com.moneyplay.MoneyPlay.domain.dto.StockDataDto;
import com.moneyplay.MoneyPlay.service.StockService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    // 국내 주식 시세 조회 URL
    @GetMapping
    public ResponseEntity<?> threeMonthStockDayChart() {
        try {
            // 한국투자증권 open api 에서 접근 토큰 발급
            StockAPITokenDto stockAPITokenDto = stockService.getApiToken();
            System.out.println("API token 받아옴!");
            StockDataDto stockDataDto = new StockDataDto(stockService.getStockData(stockAPITokenDto.getAccessToken()));
            System.out.println("해당 국내주식 기간별 시세 데이터 받아옴!");
            System.out.println(stockDataDto);
            return new ResponseEntity<>(stockDataDto, HttpStatus.OK); //추가해야함 데이터
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
