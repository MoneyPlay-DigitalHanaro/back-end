package com.moneyplay.MoneyPlay.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StockChartDto {
    private String successedCode;   // 성공 실패 여부

    private String responseCode;  // 응답 코드

    private String responseMessage;    // 응답 메세지

    private StockDataDto stockDetailData; // 응답 상세

    private List<StockDayChartDto> dayChartData; // 일별 데이터


    /*public StockChartDto (String successedCode, String responseCode, String responseMessage, String responseDetail, String dayData) {
        this.successedCode = successedCode;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.responseDetail = responseDetail;
    }*/

}
