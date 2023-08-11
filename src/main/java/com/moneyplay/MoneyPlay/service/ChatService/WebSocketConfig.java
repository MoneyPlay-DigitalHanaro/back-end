package com.moneyplay.MoneyPlay.service.ChatService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker

// WebSocket 클라이언트에서 간단한 메시징 프로토콜(예: STOMP)로 메시지 처리를 구성하는 방법
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

    // WebSocket 앤드포인트를 통해 STOMP를 등록하기 위한 메서드
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/")
                .setAllowedOrigins("*")
                .withSockJS();
    }


}
