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
    private Long DepositTypeId;

    private String DepositName;

    private Long DepositInterestRate;
}
