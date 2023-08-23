package com.moneyplay.MoneyPlay.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// 주식 일별 데이터
@Data
@AllArgsConstructor
public class StockDayChartDto {


    private String stockOpenDate;   // 주식 영업 일자

    private String stockClosePrice;     // 주식 종가

    private String stockHighPrice;      // 주식 최고가

    private String stockLowPrice;       // 주식 최저가

}
