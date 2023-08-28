package com.moneyplay.MoneyPlay.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Setter
public class Point {

    public Point() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long pointId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(nullable = false)
    private Long stockPoint;

    @Column(nullable = false)
    private Long savingPoint;

    @Setter
    @Column(nullable = false)
    private Long holdingPoint;

    public void updateHoldingPoint(Long holdingPoint) {
        this.holdingPoint = holdingPoint;
    }
<<<<<<< HEAD
=======

    public void updateStockPoint(Long stockPoint) {
        this.stockPoint = stockPoint;
    }

    public void updateDepositPoint(Long depositPoint) {
        this.savingPoint = depositPoint;
    }
>>>>>>> 1b245fd90b881b754493d7ab9a5926f2c32bc4a9
}
