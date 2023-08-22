package com.moneyplay.MoneyPlay.domain.dto;

import com.moneyplay.MoneyPlay.domain.Corporation;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
public class CorporationAddDto {

    @NotNull(message = "이름이 필요합니다.")
    private String corporationName;

    @NotNull(message = "코드가 필요합니다.")
    private String code;

    public Corporation toEntity() {

        return Corporation.builder()
                .corporationName(corporationName)
                .code(code)
                .build();
    }
}
