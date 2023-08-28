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
public class CurrentStock {

    public CurrentStock() {}

    public CurrentStock(User user, Corporation corporation, int totalPrice, int addPrice, int stockHoldingCount) {
        this.user = user;
        this.corporation = corporation;
        this.totalPrice = addPrice;
        this.stockHoldingCount = stockHoldingCount;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "current_stock_id")
    private Long currentStockId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporation_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Corporation corporation;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private int stockHoldingCount;


<<<<<<< HEAD
    public void update(int addPrice, int addStockCount) {
        this.totalPrice += addPrice;
        this.stockHoldingCount += addStockCount;
=======
    public void buyUpdate(int addPrice, int buyStockCount) {
        this.totalPrice += addPrice;
        this.stockHoldingCount += buyStockCount;
    }
    public void sellUpdate(int sellStockCount) {
        this.totalPrice -= this.totalPrice/this.stockHoldingCount * sellStockCount;
        this.stockHoldingCount -= sellStockCount;
>>>>>>> 1b245fd90b881b754493d7ab9a5926f2c32bc4a9
    }

}
