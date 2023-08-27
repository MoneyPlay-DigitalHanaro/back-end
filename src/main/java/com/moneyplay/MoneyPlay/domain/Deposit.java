package com.moneyplay.MoneyPlay.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Deposit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DepositId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long DepositAmount;

    private Long InterestAmount;

    private LocalDate StartDate;

    private LocalDate EndDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deposit_type_id")
    private DepositType depositType;

    private Long week;


}
