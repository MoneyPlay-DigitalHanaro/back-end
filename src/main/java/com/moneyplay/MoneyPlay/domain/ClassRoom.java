package com.moneyplay.MoneyPlay.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class ClassRoom {

    public ClassRoom() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_room_id")
    private Long classRoomId;

    @Column(nullable = false)
    private int studentGrade;

    @Column(nullable = false)
    private int studentClass;

    @OneToMany(mappedBy = "classRoom")
    private List<Chat> chats;

    @OneToMany(mappedBy = "classRoom")
    private List<User> users;

}
