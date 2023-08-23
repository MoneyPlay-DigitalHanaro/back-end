package com.moneyplay.MoneyPlay.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@RequiredArgsConstructor
public class UserDailyPoint {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long UserDailyPointId;

    // 학생 정보
    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    // 교실 정보
    @ManyToOne
    @JoinColumn(name = "class_room_id")
    private ClassRoom classRoom;

    // 날짜
    private LocalDate localDate;

    // 일별 포인트 총 합
    private Long TotalPoint;

    public UserDailyPoint(User user, ClassRoom classRoom, LocalDate localDate, Long currentTotal) {
        this.user = user;
        this.classRoom = classRoom;
        this.localDate = localDate;
        this.TotalPoint = currentTotal;
    }
}
