package com.moneyplay.MoneyPlay.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class DepositType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depositTypeId;

    private String DepositName;

    private double DepositInterestRate;

    private Long minAmount;

    private Long maxAmount;

    private int minMonth;

    private int maxMonth;

}
