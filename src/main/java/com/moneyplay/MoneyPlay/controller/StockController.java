package com.moneyplay.MoneyPlay.controller;

import com.moneyplay.MoneyPlay.service.StockService;
import lombok.RequiredArgsConstructor;
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
    @GetMapping("/{id}")
    public ResponseEntity<?> threeMonthStockDayChart(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(, HttpStatus.OK); //추가해야함 데이터
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
