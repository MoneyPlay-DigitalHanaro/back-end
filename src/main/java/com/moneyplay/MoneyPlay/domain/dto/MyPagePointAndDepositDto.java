package com.moneyplay.MoneyPlay.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyPagePointAndDepositDto {

    private MyPointDto myPointDto;

    private MyDepositDto myDepositDto;
}
