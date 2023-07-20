package com.moneyplay.MoneyPlay.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
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

    @OneToMany(mappedBy = "user")
    private List<Chat> chat;

    @OneToMany(mappedBy = "user")
    private List<CurrentStock> currentStock;

    @OneToOne(mappedBy = "user")
    private Point point;

    @OneToMany(mappedBy = "user")
    private List<StockTradeHistory> stockTradeHistory;
}
