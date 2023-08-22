package com.moneyplay.MoneyPlay.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moneyplay.MoneyPlay.domain.ClassDailyPoint;
import com.moneyplay.MoneyPlay.domain.ClassRoom;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.domain.UserDailyPoint;
import com.moneyplay.MoneyPlay.domain.dto.AdminDataDto;
import com.moneyplay.MoneyPlay.domain.dto.AdminDto;
import com.moneyplay.MoneyPlay.repository.ClassRoomRepository;
import com.moneyplay.MoneyPlay.repository.DailyRepository.ClassDailyPointRepository;
import com.moneyplay.MoneyPlay.repository.DailyRepository.UserDailyPointRepository;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequiredArgsConstructor
public class AdminController {

    final UserRepository userRepository;
    final ClassRoomRepository classRoomRepository;
    final UserDailyPointRepository userDailyPointRepository;
    final ClassDailyPointRepository classDailyPointRepository;

    // 메인 페이지 접속 시 그 반에 있는 학생 정보 return

    @GetMapping("/admin")
    public AdminDataDto main_page(@RequestHeader("Authorization") String token2){

        // 토큰으로 교실 고유 키 출력 하기
        String token = token2.substring(7);

        DecodedJWT decodedJWT = JWT.decode(token);
        Long id = decodedJWT.getClaim("id").asLong();

        User user = userRepository.findByuserId(id);
        ClassRoom classRooms = user.getClassRoom();

        ClassRoom classRoom = classRoomRepository.findByclassRoomId(classRooms.getClassRoomId());
        List<User> users = userRepository.findByClassRoom(classRoom);

        List<UserDailyPoint> userDailyPoints = userDailyPointRepository.findAll();
        List<ClassDailyPoint> classDailyPoints = classDailyPointRepository.findAll();

        AdminDataDto adminDataDto = new AdminDataDto(users,userDailyPoints,classDailyPoints);


        return adminDataDto;
    }


    // 포인트 일괄 지급
    @Transactional
    @PostMapping("/admin/total_increase")
    public List<User> total_increase(@RequestBody AdminDto adminDto){

        // 유저의 holding point 늘려주기

        List<Long> user = adminDto.getUser();
        int increase_point = adminDto.getIncrease_point();

        for(int i=0; i<user.size(); i++){
            User Temp_user = userRepository.findByuserId(user.get(i));
            Temp_user.getPoint().setHoldingPoint(Temp_user.getPoint().getHoldingPoint()+increase_point);
        }

        return userRepository.findAll();

    }


    // 일괄 초기화 -> 0
    @Transactional
    @PostMapping("/admin/total_init")
    @Modifying
    public List<User> total_init(){

        // 교실 고유 키 필요함

        ClassRoom classRoom = classRoomRepository.findByclassRoomId(1L);
        List<User> users = userRepository.findByClassRoom(classRoom);

        for(int i=0; i<users.size(); i++){
            users.get(i).getPoint().setHoldingPoint(0);
        }

        return userRepository.findAll();

    }








}
