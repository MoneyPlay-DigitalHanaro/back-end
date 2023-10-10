package com.moneyplay.MoneyPlay.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StockDataDto {

    private String name;       // 주식 종목 이름
    private String previousComparePrice;    // 전일 대비 가격
    private String previousCompareRate;   // 전일 대비율
    private String stockPresentPrice;   // 주식 현재가

}
