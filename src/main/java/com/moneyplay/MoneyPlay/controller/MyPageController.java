package com.moneyplay.MoneyPlay.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moneyplay.MoneyPlay.domain.CurrentStock;
import com.moneyplay.MoneyPlay.domain.Deposit;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.domain.dto.*;
import com.moneyplay.MoneyPlay.repository.CurrentStockRepository;
import com.moneyplay.MoneyPlay.repository.DepositRepository.DepositRepository;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import com.moneyplay.MoneyPlay.service.MyPageService;
import com.moneyplay.MoneyPlay.service.StockService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;
    private final StockService stockService;
    private final CurrentStockRepository currentStockRepository;
    private final DepositRepository depositRepository;
    private final UserRepository userRepository;

    @ApiOperation(value = "마이페이지 클릭 시 내 주식 상세 정보")
    @GetMapping
    public ResponseEntity<?> myPageStockDetail(@RequestHeader("Authorization") String tokens) {
        try {

            System.out.println("============= 마이페이지 첫 화면 =============");
            // 유저가 가지고 있는 주식에 대한 데이터 리스트
            List<MyStockDto> myStockDtoList = null;
            // 유저가 가지고 있는 예금에 대한 데이터
            MyDepositDto myDepositDto = null;
            // 유저의 포인트 상세 데이터
            MyPointDto myPointDto = null;


            // 헤더에서 토큰을 가져온다.
            String token =tokens.substring(7);
            // 토큰값을 decode하여 id값을 가져온다.
            DecodedJWT decodedJWT = JWT.decode(token);
            Long userId = decodedJWT.getClaim("id").asLong();


            User user = userRepository.findByuserId(userId);

            System.out.println("User 정보 가져옴. User = " + user.getStudentName());

            // 유저의 보유 주식 정보를 가져온다. (보유 주식이 없으면 null)
            List<CurrentStock> currentStockList = currentStockRepository.findByUser(user).orElseThrow(null);
            // 한국투자증권 open api 에서 접근 토큰 발급
            StockAPITokenDto stockAPITokenDto = stockService.getApiToken();
            System.out.println("API token 받아옴!");

            // 유저의 보유 주식 관련 데이터와 한국투자증권 open api 에서 내가 지정한 모든 주식 리스트 정보 가져오기
            List<StockDataDto> userStockDataList = new ArrayList<>();


            if (currentStockList == null) {
                userStockDataList = null;
                myStockDtoList = null;
            }
            else {
                for (int i = 0; i < currentStockList.size(); i++) {
                    userStockDataList.add(stockService.getStockData(stockAPITokenDto.getAccessToken(), currentStockList.get(i).getCorporation().getCode()));
                }


                // 유저의 주식 정보를 myPage 표현 양식에 맞게 가져온다.
                myStockDtoList = myPageService.findUserStock(userId, currentStockList, userStockDataList);
                System.out.println("!!유저의 주식 수= " + myStockDtoList.size());
            }

            // 유저의 적금 정보를 가져온다.
            Deposit deposit = depositRepository.findByUser(user).orElse(null);
            System.out.println("!!예금=  " + deposit);
            if (deposit != null) {
                myDepositDto = new MyDepositDto(deposit);
                System.out.println("적금 이름= " + myDepositDto.getDepositType().getDepositName());
            }
            else {
                System.out.println("예금 정보= "+ deposit);
            }

            System.out.println("예금 Dto 정보= "+ myDepositDto);
            // 유저의 포인트 정보를 가져온다.
            myPointDto = myPageService.findUserPoint(userId, myStockDtoList, myDepositDto);

            System.out.println("유저의 포인트 총합= " + myPointDto.getTotalPoint());

            // 유저의 포인트 정보와 주식 정보를 모아 리턴한다.
            MyPageMyPoiintAndStockDto myPageMyPoiintAndStockDto = new MyPageMyPoiintAndStockDto(myPointDto,myStockDtoList);
            return new ResponseEntity<>(myPageMyPoiintAndStockDto,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
