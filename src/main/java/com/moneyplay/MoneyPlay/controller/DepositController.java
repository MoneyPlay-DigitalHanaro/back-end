package com.moneyplay.MoneyPlay.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moneyplay.MoneyPlay.domain.Deposit;
import com.moneyplay.MoneyPlay.domain.DepositType;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.repository.DepositRepository.DepositRepository;
import com.moneyplay.MoneyPlay.repository.DepositRepository.DepositTypeRepository;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import com.moneyplay.MoneyPlay.service.DepositService.DepositScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class DepositController {

    final DepositRepository depositRepository;
    final DepositTypeRepository depositTypeRepository;
    final UserRepository userRepository;

    @GetMapping("game/deposit")
    public List<DepositType> depositGet() {

        // 적금 종류를 return 해주기

        return depositTypeRepository.findAll();
    }


    @PostMapping("game/deposit")
    public void depositPost(@RequestHeader("Authorization") String tokens) {

        // 적금을 선택해서 시작함

        // 적금 종류, 만기일 필요

        String token = tokens.substring(7);

        DecodedJWT decodedJWT = JWT.decode(token);
        Long id = decodedJWT.getClaim("id").asLong();

        // 고른 적금은 3L 이라고 가정

        // 적금 객체 생성
        DepositType depositType = new DepositType();
        depositType.setDepositTypeId(3L);
        depositType.setDepositName("훈이");
        depositType.setDepositInterestRate(4L);

        User user = userRepository.findByuserId(id);

        // 오늘 날짜가 28일 이상이면 다음 달 1일 시작으로 해주기

        LocalDate currentDate = LocalDate.now();

        int todayYear = currentDate.getYear();
        int todayMonth = currentDate.getMonthValue();
        int todayDay = currentDate.getDayOfMonth();

        // 적금 생성

        Deposit deposit = new Deposit();
        deposit.setDepositType(depositType); // 적금 종류 선택
        deposit.setUser(user); // 유저 설정
        deposit.setDepositAmount(10000L); // 적금 금액
        deposit.setEndDate(LocalDate.of(2023, 10, 5)); // 임의로 만기일 설정
        deposit.setInterestAmount(0L);
        deposit.setStartDate(LocalDate.of(todayYear,todayMonth,todayDay));

        depositRepository.save(deposit);

    }
}
