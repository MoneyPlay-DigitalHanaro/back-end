package com.moneyplay.MoneyPlay.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moneyplay.MoneyPlay.domain.ClassDailyPoint;
import com.moneyplay.MoneyPlay.domain.ClassRoom;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.domain.UserDailyPoint;
import com.moneyplay.MoneyPlay.domain.dto.AdminDataDto;
import com.moneyplay.MoneyPlay.domain.dto.MainDto;
import com.moneyplay.MoneyPlay.repository.ClassRoomRepository;
import com.moneyplay.MoneyPlay.repository.DailyRepository.ClassDailyPointRepository;
import com.moneyplay.MoneyPlay.repository.DailyRepository.UserDailyPointRepository;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins="*")
@RestController
@RequiredArgsConstructor
public class AdminController {

    final UserRepository userRepository;
    final ClassRoomRepository classRoomRepository;
    final UserDailyPointRepository userDailyPointRepository;
    final ClassDailyPointRepository classDailyPointRepository;


    // 메인 페이지, 학생 관리 페이지 접속

    @GetMapping({"/admin","/student"})
    public AdminDataDto main_page(@RequestHeader("Authorization") String tokens) {

        // 토큰으로 교실 고유 키 출력 하기
        String token = tokens.substring(7);

        DecodedJWT decodedJWT = JWT.decode(token);
        Long id = decodedJWT.getClaim("id").asLong();

        User user = userRepository.findByuserId(id);
        ClassRoom classRooms = user.getClassRoom();

        ClassRoom classRoom = classRoomRepository.findByclassRoomId(classRooms.getClassRoomId());
        List<User> users = userRepository.findByClassRoom(classRoom);

        List<UserDailyPoint> userDailyPoints = userDailyPointRepository.findAll();
        List<ClassDailyPoint> classDailyPoints = classDailyPointRepository.findAll();

        AdminDataDto adminDataDto = new AdminDataDto(users, userDailyPoints, classDailyPoints);


        return adminDataDto;
    }


    // 포인트 일괄 초기화

    @Transactional
    @PostMapping("/admin/total_init")
    @Modifying
    public List<User> total_init(@RequestHeader("Authorization") String tokens) {

        // 토큰으로 교실 고유 키 출력 하기
        String token = tokens.substring(7);

        DecodedJWT decodedJWT = JWT.decode(token);
        Long id = decodedJWT.getClaim("id").asLong();

        User user = userRepository.findByuserId(id);
        ClassRoom classRoom = user.getClassRoom();

        List<User> users = userRepository.findByClassRoom(classRoom);

        for (int i = 0; i < users.size(); i++) {
            users.get(i).getPoint().setHoldingPoint(0L);
            users.get(i).getPoint().setSavingPoint(0L);
            users.get(i).getPoint().setStockPoint(0L);

            userRepository.save(users.get(i));
        }

        return userRepository.findByClassRoom(classRoom);

    }


    // 포인트 일괄 지급

    @Transactional
    @PostMapping("/admin/total_increase")
    public List<User> total_increase(@RequestHeader("Authorization") String tokens, @RequestParam Long increase_point) {

        // 토큰으로 교실 고유 키 출력 하기
        String token = tokens.substring(7);

        DecodedJWT decodedJWT = JWT.decode(token);
        Long id = decodedJWT.getClaim("id").asLong();

        User user = userRepository.findByuserId(id);
        ClassRoom classRoom = user.getClassRoom();

        List<User> users = userRepository.findByClassRoom(classRoom);

        for (int i = 0; i < users.size(); i++) {
            User Temp_user = users.get(i);
            Temp_user.getPoint().setHoldingPoint(Temp_user.getPoint().getHoldingPoint() + increase_point);
            userRepository.save(Temp_user);
        }

        return userRepository.findByClassRoom(classRoom);

    }


//    // 학생 정보 수정
//
//    @Transactional
//    @PostMapping("/admin/modify")
//    public void modify(User user){
//
//        // id
//        // 이름
//        // 이메일
//        // 포인트
//
//        userRepository.save(user);
//
//
//    }





    // 메인페이지 접속 시 상위 3명 포인트 반환

    @GetMapping("/main")
    public List<MainDto> main(@RequestHeader("Authorization") String tokens){

        String token = tokens.substring(7);

        DecodedJWT decodedJWT = JWT.decode(token);
        Long id = decodedJWT.getClaim("id").asLong();

        User user = userRepository.findByuserId(id);
        ClassRoom classRooms = user.getClassRoom();

        ClassRoom classRoom = classRoomRepository.findByclassRoomId(classRooms.getClassRoomId());
        List<User> users = userRepository.findByClassRoom(classRoom);

        List<User> sortedUsers = users.stream()
                .sorted(Comparator.comparingLong(User::getTotalHoldingPoint).reversed())
                .limit(3)
                .collect(Collectors.toList());

        List<MainDto> mainDtos = new ArrayList<>();

        for(int i=0; i<3; i++){
            MainDto mainDto = new MainDto();
            mainDto.setId(sortedUsers.get(i).getUserId());
            mainDto.setName(sortedUsers.get(i).getStudentName());
            mainDto.setPoint(sortedUsers.get(i).getTotalHoldingPoint());
            mainDto.setIndex(0);
            mainDtos.add(mainDto);
        }

        MainDto mainDto = new MainDto();

        mainDto.setId(user.getUserId());
        mainDto.setName(user.getStudentName());
        mainDto.setPoint(user.getTotalHoldingPoint());
        mainDto.setIndex(1);
        mainDtos.add(mainDto);

        return mainDtos;
    }





}
