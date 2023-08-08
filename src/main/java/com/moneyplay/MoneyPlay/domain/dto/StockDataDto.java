package com.moneyplay.MoneyPlay.domain.dto;

import lombok.Data;

@Data
public class StockDataDto {

    String total;

    public StockDataDto(String total) {
        this.total = total;
    }
}
