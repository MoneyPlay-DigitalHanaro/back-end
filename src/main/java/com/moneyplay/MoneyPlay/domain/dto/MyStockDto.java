package com.moneyplay.MoneyPlay.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyStockDto {

    private String name;    // 주식 이름

    private int presentPrice;   // 현재 가격

    private int hodingStockCount;   // 가지고 있는 주식 수

    private Long totalStockValue;    // 가지고 있는 주식의 총 가치

    private Long changeStockValue;   // 가지고 있는 주식의 총 수익금액

    private double changeStockRate; // 가지고 있는 주식의 총 수익률
}
