package com.moneyplay.MoneyPlay.domain.dto;

import com.moneyplay.MoneyPlay.jwt.ErrorCode;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse<T> {
    private int status;
    private String message;
    private int code;
    private T Authorization;


    public static <T> ApplicationResponse<T> ok(ErrorCode errorCode, T Authorization) {
        ApplicationResponse<T> applicationResponse = new ApplicationResponse<>();

        applicationResponse.setStatus(errorCode.getHttpStatus().value());
        applicationResponse.setMessage(errorCode.getMessage());
        applicationResponse.setCode(errorCode.getCode());
        applicationResponse.setAuthorization(Authorization);

        return applicationResponse;
    }

}
