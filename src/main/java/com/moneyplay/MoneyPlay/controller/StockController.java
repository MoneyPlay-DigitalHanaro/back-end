package com.moneyplay.MoneyPlay.controller;

import com.moneyplay.MoneyPlay.domain.Corporation;
import com.moneyplay.MoneyPlay.domain.dto.CorporationAddDto;
import com.moneyplay.MoneyPlay.domain.dto.StockAPITokenDto;
import com.moneyplay.MoneyPlay.domain.dto.StockChartDto;
import com.moneyplay.MoneyPlay.domain.dto.StockDataDto;
import com.moneyplay.MoneyPlay.service.CorporationService;
import com.moneyplay.MoneyPlay.service.StockService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    private final CorporationService corporationService;

    // 국내 주식 시세 조회 URL
    @ApiOperation(value = "해당 주식 3개월 시세 조회")
    @GetMapping("/{name}")
    public ResponseEntity<?> stockThreeMonthDayChart(@PathVariable String name) {
        try {

            System.out.println("name= " + name);
            // 한국투자증권 open api 에서 접근 토큰 발급
            StockAPITokenDto stockAPITokenDto = stockService.getApiToken();
            System.out.println("API token 받아옴!");

            // 회사 이름으로 회사 데이터 가져오기
            Corporation corporation = corporationService.getCorporation(name);

            // 토큰과 회사데이터로 해당 회사의 3개월 시세 가져오기
            StockChartDto stockChartDto = stockService.getStockThreeMonthData(stockAPITokenDto.getAccessToken(), corporation.getCode());
            System.out.println("해당 국내주식 기간별 시세 데이터 받아옴!");
            System.out.println(stockChartDto);

            return new ResponseEntity<>(stockChartDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "모든 주식 회사 리스트 조회")
    @GetMapping
    public ResponseEntity<?> stockAllDataList() {
        try {
            // 한국투자증권 open api 에서 접근 토큰 발급
            StockAPITokenDto stockAPITokenDto = stockService.getApiToken();
            System.out.println("API token 받아옴!");

            List<StockDataDto> stockDataList = stockService.getAllStockData(stockAPITokenDto.getAccessToken());

            return new ResponseEntity<>(stockDataList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "주식 회사 추가")
    @PostMapping("/add")
    public ResponseEntity<?> corporationAdd(HttpServletRequest request, @Validated @RequestBody CorporationAddDto corporationAddDto) {
        try {
            String corporationName = corporationService.addCorporation(corporationAddDto);

            return new ResponseEntity<>(corporationName + "등록완료", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?>
}
