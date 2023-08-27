package com.moneyplay.MoneyPlay.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyPointDto {

    private int totalPoint;     // 총 포인트 가치

    private int changePointValue;       // 수익 금액

    private double changePointRate;     // 수익률

    private Long availablePoint;     // 사용가능한 포인트

    private Long totalStockPoint;       // 총 주식 포인트

    private Long totalDepositPoint;  //총 적금 포인트
}
