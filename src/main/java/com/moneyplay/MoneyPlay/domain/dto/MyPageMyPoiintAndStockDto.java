package com.moneyplay.MoneyPlay.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MyPageMyPoiintAndStockDto {

    MyPointDto myPointDto;  // 유저 포인트 정보

    List<MyStockDto> myStockDtoList;  // 유저 주식 정보
}
