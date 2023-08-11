package com.moneyplay.MoneyPlay.controller;

import com.moneyplay.MoneyPlay.domain.ClassRoom;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.repository.ClassRoomRepository;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequiredArgsConstructor
public class AdminController {

    final UserRepository userRepository;
    final ClassRoomRepository classRoomRepository;

    // 메인 페이지 접속 시 그 반에 있는 학생 정보 return

    @GetMapping("/admin")
    public List<User> main_page(){

        // 토큰으로 교실 고유 키 출력 하기

        ClassRoom classRoom = classRoomRepository.findByclassRoomId(1L);
        List<User> users = userRepository.findByClassRoom(classRoom);


        return users;
    }


    // 포인트 일괄 지급

    @PostMapping("/admin/total_increase")
    @Modifying
    @Query(value = "UPDATE User u SET u.point.holdingPoint = :increase_point + u.point.holdingPoint")
    public List<User> total_increase(@RequestBody List<Integer> user, int increase_point){

        System.out.println(user.size());

        return userRepository.findAll();

    }


    // 일괄 초기화 -> 0

    @PostMapping("/admin/total_init")
    @Modifying
    @Query(value = "UPDATE User u SET u.point.holdingPoint = 0")
    public List<User> total_init(){
        return userRepository.findAll();

    }







    // CRUD




}
