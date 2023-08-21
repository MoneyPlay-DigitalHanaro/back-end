package com.moneyplay.MoneyPlay.jwt;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class OauthTokenMap {
    private static OauthTokenMap oauthTokenMap = new OauthTokenMap();

    private HashMap<Long, OauthToken> OauthTokens = new HashMap<>(); // <userId, OauthToken>

    private OauthTokenMap() {}

    public static OauthTokenMap getInstance() {
        return oauthTokenMap;
    }
}

