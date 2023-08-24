package com.moneyplay.MoneyPlay.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyStockInfoDto {

    private int totalStockValue;        // 총 보유주식가치

    private int totalChangeStockValue;       // 내 주식 평가손익

    private double totalChangeStockRate;        // 내 주식 수익률

    private int availablePoint;     // 사용 가능한 포인트

    private int totalBuyStockPoint;      // 총 주식 매수 포인트
}
