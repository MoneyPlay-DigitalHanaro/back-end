package com.moneyplay.MoneyPlay.jwt;

public interface JwtProperties {
    String SECRET = "test";
    int EXPIRATION_TIME =  8640000;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}