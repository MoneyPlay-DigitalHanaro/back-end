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
<<<<<<< HEAD
    private Long DepositTypeId;
=======
    private Long depositTypeId;
>>>>>>> 1b245fd90b881b754493d7ab9a5926f2c32bc4a9

    private String DepositName;

    private Long DepositInterestRate;

    private Long minAmount;

    private Long maxAmount;

    private int minMonth;

    private int maxMonth;

}
