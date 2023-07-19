package com.moneyplay.MoneyPlay.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Chat {

    public Chat() {}

    // @GeneratedValue(strategy = GenerationType.IDENTITY) -> id의 기본생성전략을 데이터베이스에 위임한다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_id")
    private Long chatId;

    // 가급적이면 지연로딩만 사용 -> 즉시로딩은 N+1문제를 일으킬 수 있다.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_room_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ClassRoom classRoom;

    @Column(nullable = false)
    private String chattingMessage;

    @Column(nullable = false)
    private LocalDateTime chattingDate;
}
