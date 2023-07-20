package com.moneyplay.MoneyPlay.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class SavingProduct {

    public SavingProduct() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saving_product_id")
    private Long savingProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saving_log_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SavingLog savingLog;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int period;

    @Column(nullable = false)
    private int rate;

    @Column(nullable = false)
    private int minPrice;
}
