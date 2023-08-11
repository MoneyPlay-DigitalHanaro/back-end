package com.moneyplay.MoneyPlay.service.ChatService;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;


//텍스트 메시지만 처리하는 구현을 위한 편리한 기본 클래스
@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {

    //웹소켓 세션을 담아둘 맵

    HashMap<String, WebSocketSession> sessionMap = new HashMap<>();

    // 웹소켓 클라이언트가 텍스트 메시지를 전송할 때 호출
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

        String msg = message.getPayload();
        for(String key : sessionMap.keySet()) {
            WebSocketSession wss = sessionMap.get(key);
            try {
                wss.sendMessage(new TextMessage(msg));
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 웹소켓이 연결되면 호출되는 함수
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        super.afterConnectionEstablished(session);
        sessionMap.put(session.getId(), session);
    }

    // 웹소켓이 종료되면 호출되는 함수

//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//
//        sessionMap.remove(session.getId());
//        super.afterConnectionClosed(session, status);
//    }


}
