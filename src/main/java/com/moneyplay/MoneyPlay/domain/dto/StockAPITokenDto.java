package com.moneyplay.MoneyPlay.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockAPITokenDto {

    String accessToken;
    String tokenType;
    int expiresIn;
}
