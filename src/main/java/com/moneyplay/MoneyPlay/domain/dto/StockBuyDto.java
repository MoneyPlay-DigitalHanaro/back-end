package com.moneyplay.MoneyPlay.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockBuyDto {

    private String name;       // 주식 종목 이름
    private int stockPresentPrice;   // 주식 현재가
    private int buyAmount;      // 총 매수량
    private String tradeType;      // 매수
}
