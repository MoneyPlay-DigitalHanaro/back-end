package com.moneyplay.MoneyPlay.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class StockTradeHistory {

    public StockTradeHistory() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_trade_history_id")
    private Long stockTradeHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporation_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Corporation corporation;

    // int가 대략 20억까지 표현가능하다.
    @Column(nullable = false)
    private int stockSoldPoint;

    @Column(nullable = false)
    private int stockSoldCount;

    @Column(nullable = false)
    private String tradeType;

    @Column(nullable = false)
    private LocalDateTime tradeDate;
}
