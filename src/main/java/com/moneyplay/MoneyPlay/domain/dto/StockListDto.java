package com.moneyplay.MoneyPlay.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StockListDto {

    private MyStockInfoDto myStockInfoDto;

    private List<StockDataDto> stockDataList;
}
