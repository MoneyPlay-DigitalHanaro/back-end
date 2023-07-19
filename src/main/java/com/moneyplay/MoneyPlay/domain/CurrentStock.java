package com.moneyplay.MoneyPlay.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class CurrentStock {

    public CurrentStock() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "current_stock_id")
    private Long currentStockId;

    private User user;

    private Corporation corporation;

    @Column(nullable = false)
    private int averagePrice;

    @Column(nullable = false)
    private int stockHoldingCount;

}
