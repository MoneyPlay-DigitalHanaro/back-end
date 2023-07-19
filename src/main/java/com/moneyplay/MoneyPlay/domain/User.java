package com.moneyplay.MoneyPlay.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "school_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_room_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ClassRoom classRoom;

    @Column(nullable = false)
    private int studentNumber;

    @Column(nullable = false)
    private String studentName;

    @Column(nullable = false)
    private String eMail;

    @Column(nullable = false)
    private boolean isTeacher;

    @Column(nullable = false)
    private String studentProfile;

    @OneToOne(mappedBy = "user")
    private Chat chat;

    @OneToMany(mappedBy = "current_stock")
    private CurrentStock currentStock;

    @OneToMany(mappedBy = "point")
    private Point point;

    @OneToMany(mappedBy = "stock_trade_history")
    private StockTradeHistory stockTradeHistory;
}
