package com.moneyplay.MoneyPlay.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
public class WordToday {

    public WordToday() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Long wordTodayId;

    @Setter
    @Column(nullable = false)
    private String wordTodayName;

    @Setter
    @Column(nullable = false)
    private String Todaycontent;


}
