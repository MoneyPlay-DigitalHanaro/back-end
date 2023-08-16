package com.moneyplay.MoneyPlay.controller;

import com.moneyplay.MoneyPlay.domain.Chat;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.repository.ChatRepository;
import com.moneyplay.MoneyPlay.repository.ClassRoomRepository;
import com.moneyplay.MoneyPlay.repository.SchoolRepository;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import com.moneyplay.MoneyPlay.service.ChatService.ChatManageService;
import com.moneyplay.MoneyPlay.service.ChatService.ServerEndpointConfigurator;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpSession;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

@CrossOrigin(origins="*")
@RestController
@ServerEndpoint(value="/websocket",configurator = ServerEndpointConfigurator.class)
@RequiredArgsConstructor
public class ChatController extends Socket {

    final SchoolRepository schoolRepository;
    final ClassRoomRepository classRoomRepository;
    final ChatRepository chatRepository;
    final UserRepository userRepository;

    Long UserId;
    Long SchoolId;
    Long ClassId;
    String UserName;


    private static final List<Session> session = new ArrayList<Session>();

    @GetMapping("/chat")
    public List<String> index(HttpSession session, Model model) {

        // 기존 세션 출력해보기

//        Enumeration<?> attrName = session.getAttributeNames();
//        while (attrName.hasMoreElements()) {
//            String attr = (String) attrName.nextElement();
//            System.out.println("기존 세션 출력 : "+ session.getAttribute(attr));
//        }

        // 임시로 UserId, ClassId 지정함

        session.setAttribute("UserId",1L);
        session.setAttribute("SchoolId",1L);
        session.setAttribute("ClassId",1L);

        // 세션 출력해보기
//
//        Enumeration<?> attrNames = session.getAttributeNames();
//        while (attrNames.hasMoreElements()) {
//            String attr = (String) attrNames.nextElement();
//            System.out.println("세션 적용 출력 : "+ session.getAttribute(attr));
//        }

        // 세션 받아서 변수 초기화

        UserId = (Long) session.getAttribute("UserId");
        ClassId = (Long) session.getAttribute("ClassId");
        SchoolId = (Long) session.getAttribute("SchoolId");



        // chatManageService 객체 생성

        ChatManageService chatManageService = new ChatManageService(schoolRepository,classRoomRepository,chatRepository,userRepository);
        List<Chat> message = chatManageService.ChatHistory(SchoolId,ClassId,UserId);

//        System.out.println("message size : "+message.size());

        // 과거 채팅 담을 List 생성

        List<String> user = new ArrayList<>();
        List<String> chat = new ArrayList<>();

        // List에 과거 채팅 담기


//        for(int i=0; i<message.size(); i++){
//
//            User temp_user = message.get(i).getUser();
//            String temp_name = temp_user.getStudentName();
//
//            chat.put(temp_name,message.get(i).getChattingMessage());
//        }

        for (Chat item : message) {

            User temp_user = userRepository.findByuserId(item.getUser().getUserId());
            String temp_name = temp_user.getStudentName();

            user.add(temp_name);
            chat.add(item.getChattingMessage());

        }

        System.out.println("chat size : "+chat.size());

        // model 로 chat 넘기기

        model.addAttribute("user",user);
        model.addAttribute("chat",chat);

        return chat;

    }

    // 웹 소켓이 연결되면 호출되는 이벤트

    @OnOpen
    public void open(Session User) {
        session.add(User);
    }


    // 웹 소켓으로부터 메시지가 오면 호출되는 이벤트

    @OnMessage
    public void getMsg(Session recieveSession, String msg) {

        // chatManageService 객체 생성
        ChatManageService chatManageService = new ChatManageService(schoolRepository, classRoomRepository, chatRepository, userRepository);

        // 유저 이름 찾기
        UserName = chatManageService.ChatManageService(UserId, SchoolId, ClassId, msg);

        for (int i = 0; i < session.size(); i++) {

                try {
                    session.get(i).getBasicRemote().sendText(UserName+" : "+msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
