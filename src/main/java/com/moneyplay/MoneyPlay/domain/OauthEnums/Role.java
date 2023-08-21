package com.moneyplay.MoneyPlay.domain.OauthEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {

    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER");

    private final String value;
}