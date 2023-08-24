package com.moneyplay.MoneyPlay.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moneyplay.MoneyPlay.domain.Corporation;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.domain.dto.*;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import com.moneyplay.MoneyPlay.service.CorporationService;
import com.moneyplay.MoneyPlay.service.StockService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    private final CorporationService corporationService;

    private final UserRepository userRepository;

    // 국내 주식 시세 조회 URL
    @ApiOperation(value = "해당 주식 3개월 시세 조회")
    @GetMapping("/{name}")
    public ResponseEntity<?> stockThreeMonthDayChart(@PathVariable String name) {
        try {

            System.out.println("name= " + name);
            // 한국투자증권 open api 에서 접근 토큰 발급
            StockAPITokenDto stockAPITokenDto = stockService.getApiToken();
            System.out.println("API token 받아옴!");

            // 회사 이름으로 회사 데이터 가져오기
            Corporation corporation = corporationService.getCorporation(name);

            // 토큰과 회사데이터로 해당 회사의 3개월 시세 가져오기
            StockChartDto stockChartDto = stockService.getStockThreeMonthData(stockAPITokenDto.getAccessToken(), corporation.getCode());
            System.out.println("해당 국내주식 기간별 시세 데이터 받아옴!");
            System.out.println(stockChartDto);

            return new ResponseEntity<>(stockChartDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "모든 주식 회사 리스트 조회")
    @GetMapping
    public ResponseEntity<?> stockAllDataList(@RequestHeader("Authorization") String tokens) {
        try {
            // 헤더에서 토큰을 가져온다.
            String token =tokens.substring(7);
            // 토큰값을 decode하여 id값을 가져온다.
            DecodedJWT decodedJWT = JWT.decode(token);
            Long userId = decodedJWT.getClaim("id").asLong();

            // 한국투자증권 open api 에서 접근 토큰 발급
            StockAPITokenDto stockAPITokenDto = stockService.getApiToken();
            System.out.println("API token 받아옴!");

            // 유저의 보유 주식 관련 데이터와 한국투자증권 open api 에서 내가 지정한 모든 주식 리스트 정보 가져오기
            List<StockDataDto> stockDataList = stockService.getAllStockData(stockAPITokenDto.getAccessToken());

            // 주식 리스트 페이지의 내 주식 데이터를 표현하기 위한 dto (보유 주식이 없다면 null)
            MyStockInfoDto myStockInfoDto = stockService.getMyStockInfo(userId, stockDataList);

            // 유저의 보유 주식 데이터와 주식 데이터들을 합쳐 dto로 반환
            StockListDto stockListDto = new StockListDto(myStockInfoDto, stockDataList);

            return new ResponseEntity<>(stockListDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @ApiOperation(value = "주식 매수")
    @PostMapping("/buy")
    public ResponseEntity<?> stockBuy(@RequestHeader("Authorization") String tokens, @Validated @RequestBody StockBuyDto stockBuyDto) {

        try {
            // 헤더에서 토큰을 가져온다.
            String token =tokens.substring(7);

            // 토큰값을 decode하여 id값을 가져온다.
            DecodedJWT decodedJWT = JWT.decode(token);
            Long id = decodedJWT.getClaim("id").asLong();

            // userId를 이용해 유저 정보를 가져온다.
            User user = userRepository.findByUserId(id).orElseThrow(
                    () -> new NoSuchElementException("해당 유저에 대한 정보가 없습니다.")
            );

            // 유저가 매수 가능한 포인트를 가지고 있는지 확인
            if (user.getPoint().getHoldingPoint() >= stockBuyDto.getStockPresentPrice()*stockBuyDto.getBuyAmount()) {
                stockService.buyStock(user, stockBuyDto);
            }
            else {
                throw new Exception("매수할 금액이 부족합니다.");
            }

            return new ResponseEntity<>("매수 성공",HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "주식 매도")
    @PostMapping("/sell")
    public ResponseEntity<?> stockSell(@RequestHeader("Authorization") String tokens, @Validated @RequestBody StockBuyDto stockBuyDto) {
        try {

            return new ResponseEntity<>("매도 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
