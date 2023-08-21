package com.moneyplay.MoneyPlay.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    SUCCESS_OK(HttpStatus.OK, "성공", 1000),

    // 정석대로는 헤더필드의 Location에 URI를 넣어서 반환해야되는데 구현 할까요...?
    SUCCESS_CREATED(HttpStatus.CREATED, "생성 성공", 1001),

    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않는 토큰입니다.", 2001),

    INVALID_JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다.", 2002),

    AUTH_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 사용자를 찾을 수 없습니다.", 2003),

    AUTH_INVALID_KAKAO_CODE(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", 2004),

    AUTH_EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 엑세스 토큰입니다.", 2005),

    AUTH_BAD_LOGOUT_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다", 2006),

    BOARD_BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", 3001),

    // 채팅방 userList에 {userUUID, userID}로 매핑된 정보가 없음
    TALK_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 사용자를 찾을 수 없습니다.", 5001),

    // user의 DB 데이터에 접근할 수 없음
    TALK_USER_ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 사용자를 찾을 수 없습니다.", 5002),

    // 잘못된 location
    TALK_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 채팅방을 찾을 수 없습니다.", 5003),

    // 이미 접속중인 사용자
    TALK_DUPLICATED_USER(HttpStatus.NOT_ACCEPTABLE, "중복된 사용자입니다.", 5004),

    UNKNOWN_ERROR(HttpStatus.BAD_GATEWAY, "알 수 없는 오류입니다",6001),
    ;


    private final HttpStatus httpStatus;
    private final String message;
    private final int code;
}
