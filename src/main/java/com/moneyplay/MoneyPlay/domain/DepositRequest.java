package com.moneyplay.MoneyPlay.domain;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class DepositRequest {
    private Long increase_money;
    private Long depositId;
    private Long week;

    // getters, setters, constructors 등이 필요합니다...
}