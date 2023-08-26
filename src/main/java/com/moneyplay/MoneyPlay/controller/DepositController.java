package com.moneyplay.MoneyPlay.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moneyplay.MoneyPlay.domain.Deposit;
import com.moneyplay.MoneyPlay.domain.DepositType;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.repository.DepositRepository.DepositRepository;
import com.moneyplay.MoneyPlay.repository.DepositRepository.DepositTypeRepository;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class DepositController {

    final DepositRepository depositRepository;
    final DepositTypeRepository depositTypeRepository;
    final UserRepository userRepository;

    // 전체 적금 종류 return

    @GetMapping("game/deposit")
    public List<DepositType> depositGet() {

        // 적금 종류를 return 해주기

        return depositTypeRepository.findAll();
    }


    // 특정 예금 정보 return

    @GetMapping("savings/join/")
    public DepositType depositGet(@RequestParam Long index) {

        // 적금 종류를 return 해주기

        return depositTypeRepository.findByDepositTypeId(index);
    }


    @PostMapping("game/deposit")
    public List<Deposit> depositPost(@RequestHeader("Authorization") String tokens,
                                     @RequestParam Long increase_money,
                                     @RequestParam Long depositId,
                                     @RequestParam Long week ) {


        // 적금 종류, 만기일 필요

        String token = tokens.substring(7);

        DecodedJWT decodedJWT = JWT.decode(token);
        Long id = decodedJWT.getClaim("id").asLong();
        User user = userRepository.findByuserId(id);

        // 예금양, 시작일, 종료일, 이자율, 이자 금액, 유저 고유 아이디, 에금 고유 아이디 로 에금 생성

        LocalDate currentDate = LocalDate.now();

        Deposit deposit = new Deposit();
        deposit.setUser(user);
        deposit.setDepositId(depositId);
        deposit.setStartDate(currentDate);
        deposit.setEndDate(currentDate.plus(week, ChronoUnit.WEEKS));
        deposit.setInterestAmount(0L);
        deposit.setDepositAmount(increase_money);

        depositRepository.save(deposit);

        // 사용자 holding point 줄이기

        user.getPoint().setHoldingPoint(user.getPoint().getHoldingPoint() - increase_money);
        user.getPoint().setSavingPoint(user.getPoint().getSavingPoint() + increase_money);
        userRepository.save(user);

        return depositRepository.findAll();
    }
}
