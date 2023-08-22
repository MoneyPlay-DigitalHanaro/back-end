package com.moneyplay.MoneyPlay.controller;

import com.moneyplay.MoneyPlay.domain.Corporation;
import com.moneyplay.MoneyPlay.domain.dto.CorporationAddDto;
import com.moneyplay.MoneyPlay.domain.dto.StockAPITokenDto;
import com.moneyplay.MoneyPlay.domain.dto.StockChartDto;
import com.moneyplay.MoneyPlay.domain.dto.StockDataDto;
import com.moneyplay.MoneyPlay.service.StockService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    // 국내 주식 시세 조회 URL
    @ApiOperation(value = "해당 주식 3개월 시세 조회")
    @GetMapping
    public ResponseEntity<?> threeMonthStockDayChart() {
        try {
            // 한국투자증권 open api 에서 접근 토큰 발급
            StockAPITokenDto stockAPITokenDto = stockService.getApiToken();
            System.out.println("API token 받아옴!");
            //StockDataDto stockDataDto = new StockDataDto(stockService.getStockData(stockAPITokenDto.getAccessToken()));
            StockChartDto stockChartDto = stockService.getStockData(stockAPITokenDto.getAccessToken());
            System.out.println("해당 국내주식 기간별 시세 데이터 받아옴!");
            //System.out.println(stockDataDto);
            System.out.println(stockChartDto);
            return new ResponseEntity<>(stockChartDto, HttpStatus.OK); //추가해야함 데이터
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "주식 회사 추가")
    @PostMapping("/add")
    private ResponseEntity<?> corporationAdd(HttpServletRequest request, @Validated @RequestBody CorporationAddDto corporationAddDto) {
        try {
            String corporationName = stockService.addCorporation(corporationAddDto);

            return new ResponseEntity<>(corporationName + "등록완료", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
