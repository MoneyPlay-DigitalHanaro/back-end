package com.moneyplay.MoneyPlay.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockSellDto {

    private String name;       // 주식 종목 이름
    private int stockPresentPrice;   // 주식 현재가
    private int sellAmount;      // 총 매도량
    private String tradeType;      // 매도
}
