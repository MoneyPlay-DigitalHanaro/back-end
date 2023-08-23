package com.moneyplay.MoneyPlay.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Getter
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long boardId;

    // 교실
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_room_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ClassRoom classRoom;

    // 학생
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    // 채팅 내용
    String message;


    public Board(User user, ClassRoom classRoom, String message) {
        this.user = user;
        this.classRoom = classRoom;
        this.message = message;
    }


}
