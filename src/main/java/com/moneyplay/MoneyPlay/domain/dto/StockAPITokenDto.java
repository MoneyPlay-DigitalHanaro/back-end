package com.moneyplay.MoneyPlay.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockAPITokenDto {

    private String accessToken;
    private String tokenType;
    private int expiresIn;
}
