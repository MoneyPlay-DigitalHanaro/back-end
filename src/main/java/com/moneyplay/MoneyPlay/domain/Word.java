package com.moneyplay.MoneyPlay.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
public class Word {

    public Word() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Long wordId;

    @Column(nullable = false)
    private String wordName;

    @Column(nullable = false)
    private String content;
}
