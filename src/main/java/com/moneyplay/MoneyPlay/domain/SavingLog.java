package com.moneyplay.MoneyPlay.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class SavingLog {

    public SavingLog() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saving_log_id")
    private Long savingLogId;

    private SavingProduct savingProduct;

    private User user;

    @Column(nullable = false)
    private int payment;
}
