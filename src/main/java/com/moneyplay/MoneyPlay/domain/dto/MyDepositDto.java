package com.moneyplay.MoneyPlay.domain.dto;

import com.moneyplay.MoneyPlay.domain.Deposit;
import com.moneyplay.MoneyPlay.domain.DepositType;
import com.moneyplay.MoneyPlay.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MyDepositDto {

    public MyDepositDto() {}

    public MyDepositDto(Deposit deposit) {
        this.DepositAmount = deposit.getDepositAmount();
        this.InterestAmount = deposit.getInterestAmount();
        this.StartDate = deposit.getStartDate();
        this.EndDate = deposit.getEndDate();
        this.depositType = deposit.getDepositType();
        this.week = deposit.getWeek();
    }

    private Long DepositAmount;     // 예금 총 금액

    private Long InterestAmount;    // 예금 총 이자

    private LocalDate StartDate;    // 예금 시작일

    private LocalDate EndDate;      // 예금 종료일

    private DepositType depositType;    // 예금 종류

    private Long week;      // 예금 기간 (주 단위)
}
