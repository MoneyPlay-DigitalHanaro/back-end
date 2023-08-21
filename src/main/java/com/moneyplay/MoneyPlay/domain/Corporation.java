package com.moneyplay.MoneyPlay.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Corporation {

    public Corporation() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "corporation_id")
    private Long corporationId;

    @Column(nullable = false)
    private String corporationName;

    @Column(nullable = false)
    private String code;

}
