package com.moneyplay.MoneyPlay.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String exception = (String) request.getAttribute(JwtProperties.HEADER_STRING);
        System.out.println(exception);
        String errorCode;

//        if(exception.equals("토큰이 만료되었습니다.")) {
//            errorCode = "토큰이 만료되었습니다.";
//            setResponse (response, errorCode);
//        }
        if("토큰이 만료되었습니다.".equals(exception)) {
            errorCode = "토큰이 만료되었습니다.";
            setResponse (response, errorCode);
        }

//        if(exception.equals("유효하지 않은 토큰입니다.")) {
//            errorCode = "유효하지 않은 토큰입니다.";
//            setResponse(response, errorCode);
//        }
        if("유효하지 않은 토큰입니다.".equals(exception)) {
            errorCode = "유효하지 않은 토큰입니다.";
            setResponse(response, errorCode);
        }
    }

    private void setResponse(HttpServletResponse response, String errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(JwtProperties.HEADER_STRING + " : " + errorCode);
    }
}

