package com.moneyplay.MoneyPlay.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moneyplay.MoneyPlay.domain.CurrentStock;
import com.moneyplay.MoneyPlay.domain.dto.*;
import com.moneyplay.MoneyPlay.repository.CurrentStockRepository;
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

    @ApiOperation(value = "마이페이지 클릭 시 내 주식 상세 정보")
    @GetMapping
    public ResponseEntity<?> myPageStockDetail(@RequestHeader("Authorization") String tokens) {
        try {
            // 헤더에서 토큰을 가져온다.
            String token =tokens.substring(7);
            // 토큰값을 decode하여 id값을 가져온다.
            DecodedJWT decodedJWT = JWT.decode(token);
            Long userId = decodedJWT.getClaim("id").asLong();

            // 유저의 보유 주식 정보를 가져온다. (보유 주식이 없으면 null)
            List<CurrentStock> currentStockList = currentStockRepository.findByUserId(userId).orElseThrow(null);
            // 한국투자증권 open api 에서 접근 토큰 발급
            StockAPITokenDto stockAPITokenDto = stockService.getApiToken();
            System.out.println("API token 받아옴!");

            // 유저의 보유 주식 관련 데이터와 한국투자증권 open api 에서 내가 지정한 모든 주식 리스트 정보 가져오기
            List<StockDataDto> userStockDataList = new ArrayList<>();

            // 유저가 가지고 있는 주식에 대한 데이터 리스트
            List<MyStockDto> myStockDtoList;
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
            }
            // 유저의 적금 정보를 가져온다.

            // 유저의 포인트 정보를 가져온다.
            MyPointDto myPointDto = myPageService.findUserPoint(userId, );


            // 유저의 포인트 정보와 주식 정보를 모아 리턴한다.
            MyPageMyPoiintAndStockDto myPageMyPoiintAndStockDto = new MyPageMyPoiintAndStockDto(myPointDto,myStockDtoList);
            return new ResponseEntity<>(myPageMyPoiintAndStockDto,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
