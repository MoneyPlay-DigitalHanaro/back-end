package com.moneyplay.MoneyPlay.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Point {

    public Point() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long pointId;


    private User user;

    @Column(nullable = false)
    private int stockPoint;

    @Column(nullable = false)
    private int savingPoint;

    @Column(nullable = false)
    private int holdingPoing;
}
