package com.moneyplay.MoneyPlay.service.ChatService;

import com.moneyplay.MoneyPlay.domain.Chat;
import com.moneyplay.MoneyPlay.domain.ClassRoom;
import com.moneyplay.MoneyPlay.domain.School;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.repository.ChatRepository;
import com.moneyplay.MoneyPlay.repository.ClassRoomRepository;
import com.moneyplay.MoneyPlay.repository.SchoolRepository;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class ChatManageService {

    final SchoolRepository schoolRepository;
    final ClassRoomRepository classRoomRepository ;
    final ChatRepository chatRepository;
    final UserRepository userRepository;

    // 기본 채팅 서비스 기능 구현 메서드

    public String ChatManageService(Long UserId,Long SchoolId, Long ClassId,String msg) {

        // UserName 찾기
        User user = userRepository.findByuserId(UserId);
        String UserName = user.getStudentName();

        // 학교 객체 찾기
        School school = schoolRepository.findByschoolId(SchoolId);

        // 교실 객체 찾기
        ClassRoom classRoom = classRoomRepository.findByclassRoomId(ClassId);


        // 현재 시간
        LocalDateTime currentTime = LocalDateTime.now();

        // chat 생성자 생성

        Chat chat = new Chat();
        chat.setChat(school, user, classRoom, msg, currentTime);

        // chat DB에 저장

        chatRepository.save(chat);

        // 유저 이름 return
        return UserName;
    }




    // 과거 채팅 목록 리턴 메서드

    public List<Chat> ChatHistory(Long SchoolId, Long ClassId, Long UserId) {

        School school = schoolRepository.findByschoolId(SchoolId);
        ClassRoom classRoom = classRoomRepository.findByclassRoomId(ClassId);

        // 필터링 한 채팅 리스트

        List<Chat> SchoolChat = chatRepository.findBySchoolAndClassRoom(school, classRoom);

        System.out.println(SchoolChat.size());

        return SchoolChat;

    }
}
