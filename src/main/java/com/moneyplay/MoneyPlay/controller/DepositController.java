package com.moneyplay.MoneyPlay.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moneyplay.MoneyPlay.domain.Deposit;
<<<<<<< HEAD
=======
import com.moneyplay.MoneyPlay.domain.DepositRequest;
>>>>>>> 1b245fd90b881b754493d7ab9a5926f2c32bc4a9
import com.moneyplay.MoneyPlay.domain.DepositType;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.repository.DepositRepository.DepositRepository;
import com.moneyplay.MoneyPlay.repository.DepositRepository.DepositTypeRepository;
import com.moneyplay.MoneyPlay.repository.UserRepository;
<<<<<<< HEAD
import com.moneyplay.MoneyPlay.service.DepositService.DepositScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
=======
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
>>>>>>> 1b245fd90b881b754493d7ab9a5926f2c32bc4a9
import java.util.List;


@RestController
@RequiredArgsConstructor
public class DepositController {

    final DepositRepository depositRepository;
    final DepositTypeRepository depositTypeRepository;
    final UserRepository userRepository;

<<<<<<< HEAD
=======
    // 전체 적금 종류 return

>>>>>>> 1b245fd90b881b754493d7ab9a5926f2c32bc4a9
    @GetMapping("game/deposit")
    public List<DepositType> depositGet() {

        // 적금 종류를 return 해주기

        return depositTypeRepository.findAll();
    }


<<<<<<< HEAD
    @PostMapping("game/deposit")
    public void depositPost(@RequestHeader("Authorization") String tokens) {

        // 적금을 선택해서 시작함

=======
    // 특정 예금 정보 return

    @GetMapping("savings/join")
    public DepositType depositGet(@RequestParam Long index) {

        // 적금 종류를 return 해주기

        //
        return depositTypeRepository.findByDepositTypeId(index);
    }


    @PostMapping("game/deposit")
    public List<Deposit> depositPost(@RequestHeader("Authorization") String tokens,
                                     @RequestBody DepositRequest depositRequest) {

        Long increase_money = depositRequest.getIncrease_money();
//        Long depositId = depositRequest.getDepositId();
        Long depositId = 1L;
        Long week = depositRequest.getWeek();
>>>>>>> 1b245fd90b881b754493d7ab9a5926f2c32bc4a9
        // 적금 종류, 만기일 필요

        String token = tokens.substring(7);

        DecodedJWT decodedJWT = JWT.decode(token);
        Long id = decodedJWT.getClaim("id").asLong();
<<<<<<< HEAD

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

=======
        User user = userRepository.findByuserId(id);

        // 예금양, 시작일, 종료일, 이자율, 이자 금액, 유저 고유 아이디, 에금 고유 아이디, 주 로 에금 생성

        LocalDate currentDate = LocalDate.now();

        Deposit deposit = new Deposit();
        deposit.setUser(user);
        deposit.setDepositId(depositId);
        deposit.setStartDate(currentDate);
        deposit.setEndDate(currentDate.plus(week, ChronoUnit.WEEKS));
        deposit.setInterestAmount(0L);
        deposit.setDepositAmount(increase_money);
        deposit.setWeek(week);

        depositRepository.save(deposit);

        // 사용자 holding point 줄이기

        user.getPoint().setHoldingPoint(user.getPoint().getHoldingPoint() - increase_money);
        user.getPoint().setSavingPoint(user.getPoint().getSavingPoint() + increase_money);
        userRepository.save(user);

        return depositRepository.findAll();
>>>>>>> 1b245fd90b881b754493d7ab9a5926f2c32bc4a9
    }
}
