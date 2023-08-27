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

    private Long DepositAmount;

    private Long InterestAmount;

    private LocalDate StartDate;

    private LocalDate EndDate;

    private DepositType depositType;

    private Long week;
}
