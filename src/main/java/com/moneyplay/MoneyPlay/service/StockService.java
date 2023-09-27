package com.moneyplay.MoneyPlay.service;

import com.moneyplay.MoneyPlay.domain.*;
import com.moneyplay.MoneyPlay.domain.dto.*;
import com.moneyplay.MoneyPlay.repository.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class StockService {

    private final CorporationRepository corporationRepository;

    private final StockTradeHistoryRepository stockTradeHistoryRepository;

    private final CurrentStockRepository currentStockRepository;

    private final PointRepository pointRepository;

    private final UserRepository userRepository;

    private static final String appkey="PS9nb75j4sbAZVA5kyUNvHQ3QimvA7tPNR48";

    private static final String appsecret="9W4X6Nqet+NT8yDzLJLgl/4OUOiu7idrnNYouVTjlBPx1qu7yWzdSr+T8JrA1ChnAR2FspG7W7/Am7A89d1R2w37Arrdi/k9Zq81K3wLS2bj5adRYwCS07YLCplpWwO/GCBGwEx1r4/GuuIEzOuuqPskk74BPcaoM4Ya/BueW4CNU+VTYNo=";


    //ObjectMapper mapper;

    // 한국 투자 증권 open api 토큰 발급
/*    public StockAPITokenDto getApiToken(){
        String totalUrl = "https://openapivts.koreainvestment.com:29443//oauth2/tokenP";
        String requestBody = "{\n" +
                "    \"grant_type\": \"client_credentials\",\n" +
                "    \"appkey\": \"PSOcm47UhZZjH5638ilgaAcsQU8mKHrxtnza\",\n" +
                "    \"appsecret\": \"QJFlWFe02Ae+pKF2B7AOJwN9IsqyPSXR9ozWn45Q55/nq2awQ4Jt18G9/0zq4+pm+rZkZmXli4qTUeTbYTQg8nZc40K95RlyJHah4PyRFzuwv6enRZHjAT9xB7Psvliu4eutG8lLBqiEsQJKXbWEVEQDuvV9JIK3q5BIHEu13cYgCEjRS9w=\"\n" +
                "}";
        try {
            URL url = new URL(totalUrl);
            // url에서 URLConnection 객체를 얻어온다.
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 전송방식 -> post 요철으로 설정
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type", "application/json; utf8");
            //URLConnection에 대한 doOutput 필드값을 지정된 값으로 설정한다.
            //URL 연결은 입출력에 사용될 수 있다.
            //URL 연결을 출력용으로 사용하려는 경우 DoOutput 플래그를 true로 설정하고,
            //그렇지 않은 경우는 false로 설정해야 한다. 기본값은 false이다.
            // true로 설정하면 자동으로 POST로 설정된다. output으로 post값을 넣어주자
            conn.setDoOutput(true);

            try(OutputStream os = conn.getOutputStream()) {
                byte[] requestData = requestBody.getBytes("utf-8");
                os.write(requestData);
                os.flush();
            }

            conn.connect();

            System.out.println("http 요청 방식" + "POST BODY JSON");
            System.out.println("http 요청 타입" + "application/json; utf-8");
            System.out.println("http 요청 주소" + totalUrl);
            System.out.println("");

            int responseCode = conn.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                // 정상적인 응답을 받은 경우
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String response = br.readLine();
                System.out.println("토큰 로직의 br = " + br);
                System.out.println("토큰 로직의 response = " + response);
                System.out.println("");
                br.close();

                // JSON 형식의 응답 데이터를 파싱
                JSONObject jsonResponse = new JSONObject(response);
                String accessToken = jsonResponse.optString("access_token", "");
                String tokenType = jsonResponse.optString("token_type", "");
                int expiresIn = jsonResponse.optInt("expires_in", 0);
                System.out.println("접속 토큰 : " + accessToken);
                System.out.println("토큰 유형 : " + tokenType);
                System.out.println("expiresIn : " + expiresIn);
                return new StockAPITokenDto(accessToken, tokenType, expiresIn);
            } else {
                // 에러 응답을 받은 경우
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
                String errorResponse = br.readLine();
                br.close();
                System.out.println("에러 응답: " + errorResponse);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }*/

    // 잦은 토큰발급으로 인해 한국투자증권에서 경고 문자가 와서 토큰 발큽하는 api를 아래와 같이 대체하여 처리했다.
    public StockAPITokenDto getApiToken(){

        String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6ImRhZjVjZmM3LTZjNjAtNDY3My05YjFiLTRlMzUxODdlNmM3OCIsImlzcyI6InVub2d3IiwiZXhwIjoxNjk1ODc4MjU0LCJpYXQiOjE2OTU3OTE4NTQsImp0aSI6IlBTOW5iNzVqNHNiQVpWQTVreVVOdkhRM1FpbXZBN3RQTlI0OCJ9.uKDw6GYqM88uIk1VORQfhBGKS7EMaMpRuCdVf0KYb5QfMEsAqbgTR_VqIlzeqYH0tNSfryxiVDVjAyRucxCRrg";
        String tokenType = "Bearer";
        int expiresIn = 86400;
        System.out.println("접속 토큰 : " + accessToken);
        System.out.println("토큰 유형 : " + tokenType);
        System.out.println("expiresIn : " + expiresIn);
        return new StockAPITokenDto(accessToken, tokenType, expiresIn);
    }

    // 국내주식기간별시세
    // "처리코드, 응답상세, 일별데이터 배열"  이렇게 세 종류의 데이터를 받음
    /*public StockDataDto getStockData() {
        String url = "https://openapivts.koreainvestment.com:29443/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice";
        String requestBody =

        return null;
    }*/
    public StockChartDto getStockThreeMonthData(String accessToken, String code) {

        System.out.println("======== 국내주식기간별 시세 받아오는 로직 start======");
        System.out.println("");

        // 오늘 날짜를 문자 YYYYMMDD  포맷으로 가져온다.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        // 현재 날짜
        String today = sdf.format(c1.getTime());
        c1.add(Calendar.MONTH, -3);
        // 3달전 날짜
        String threeMonthAgo = sdf.format(c1.getTime());


        System.out.println("Today= " + today + " ThreeMonthAgo= " + threeMonthAgo);

        String requestUrl = "https://openapivts.koreainvestment.com:29443/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice?FID_COND_MRKT_DIV_CODE=J&FID_INPUT_ISCD="+ code +"&FID_INPUT_DATE_1="+ threeMonthAgo +"&FID_INPUT_DATE_2="+ today +"&FID_PERIOD_DIV_CODE=D&FID_ORG_ADJ_PRC=1";
        /*String requestParam = "{\n" +
                "    \"FID_COND_MRKT_DIV_CODE\": \"J\",\n" +
                "    \"FID_INPUT_ISCD\": \"005930\",\n" +
                "    \"FID_INPUT_DATE_1\": \"20220501\",\n" +
                "    \"FID_INPUT_DATE_2\": \"20220530\",\n" +
                "    \"FID_PERIOD_DIV_CODE\": \"D\",\n" +
                "    \"FID_ORG_ADJ_PRC\": \"1\"\n" +
                "}";*/

        HttpURLConnection conn = null;
        BufferedReader br = null;
        String responseData = "";

        StringBuffer sb = new StringBuffer();
        String returnData = "";

        try {
            URL url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();

            // Set the request method and content type
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type", "application/json; utf8");
            conn.setRequestProperty("authorization", "Bearer " + accessToken);
            conn.setRequestProperty("appkey", appkey);
            conn.setRequestProperty("appsecret", appsecret);
            conn.setRequestProperty("tr_id", "FHKST03010100");
            conn.setRequestProperty("custtype", "P");


            // Allow output for POST data
            conn.setDoOutput(true);

            // Http POST 요청을 통해 서버에 데이터를 전송합니다.
            try (OutputStream os = conn.getOutputStream()) {  // 객체 생성
                //byte requestData[] = requestParam.getBytes("utf-8");
                //System.out.println("requestData : " + requestData);
                //os.write(requestData);
                os.flush();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            conn.connect();
            System.out.println("http 요청 방식" + "POST BODY JSON");
            System.out.println("http 요청 타입" + "application/json");
            System.out.println("http 요청 주소" + requestUrl);
            System.out.println("");

            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            System.out.println("버퍼 처리 정상!");
        } catch (IOException e){
            e.printStackTrace();
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            System.out.println("예외가 발생하여 conn 에러스트림처리함.");
        } finally {
            try {
                sb = new StringBuffer();
                responseData = br.readLine();

                System.out.println();
                System.out.println("br = " + br);
                System.out.println("sb = " + sb);
                System.out.println();

                returnData = sb.toString();
                String responseCode = String.valueOf(conn.getResponseCode());
                System.out.println("http 응답 코드 : " + responseCode);
                System.out.println("http 응답 데이터 : " + returnData);

                // JSON 형식의 응답 데이터를 파싱
                JSONObject jsonResponse = new JSONObject(responseData);
                String successedCode = jsonResponse.optString("rt_cd", "");
                responseCode = jsonResponse.optString("msg_cd", "");
                String responseMessage = jsonResponse.optString("msg1", "");
                // 주식 상세 정보내용을 객체로 받아옴
                JSONObject stockData = jsonResponse.getJSONObject("output1");

                // 주식 상세 정보를 하나씩 문자열로 저장

                // 주식 종목 이름
                String corporationName = stockData.optString("hts_kor_isnm", "");
                // 전일 대비 가격
                String previousComparePrice = stockData.optString("prdy_vrss", "");
                // 전일 대비율
                String previousCompareRate = stockData.optString("prdy_ctrt", "");
                // 주식 현재 가격
                String stockPresentPrice = stockData.optString("stck_prpr", "");

                // 위에서 문자열로 받은 데이터를 이용해 주식 상세정보 Dto 객체 설정
                StockDataDto stockDataDto = new StockDataDto(corporationName, previousComparePrice, previousCompareRate, stockPresentPrice);

                // output2의 리스트 데이터를 JSONArray로 받아온다.
                JSONArray dayChartData = jsonResponse.getJSONArray("output2");
                List<StockDayChartDto> dayChart = new ArrayList<>();

                // 리스트의 각 요소를 객체처리하여 List에 추가한다.
                for(int i=0; i<dayChartData.length(); i++) {
                    // 주식 영업 일자
                    String stockOpenDate = dayChartData.getJSONObject(i).get("stck_bsop_date").toString();
                    // 주식 종가
                    String stockClosePrice = dayChartData.getJSONObject(i).get("stck_clpr").toString();
                    // 주식 최고가
                    String stockHighPrice = dayChartData.getJSONObject(i).get("stck_hgpr").toString();
                    // 주식 최저가
                    String stockLowPrice = dayChartData.getJSONObject(i).get("stck_lwpr").toString();

                    StockDayChartDto stockDayChartDto = new StockDayChartDto(stockOpenDate, stockClosePrice, stockHighPrice, stockLowPrice);
                    dayChart.add(stockDayChartDto);
                }

                System.out.println("successedCode = " + successedCode);
                System.out.println("responseCode = " + responseCode);
                System.out.println("responseMessage = " + responseMessage);
                System.out.println("stockData = " + stockDataDto);
                System.out.println("stockDayChart = " + dayChart);

                if (br != null){
                    br.close();
                    // StockController에 StockChartDto형식으로 반환
                    return new StockChartDto(successedCode, successedCode, responseMessage, stockDataDto, dayChart);
                }
            } catch (IOException e) {
                throw new RuntimeException("API 응답을 읽는데 실패했습니댜.",e);
            }
        }
        return null;
    }

    // 해당 주식에 대한 정보를 반환
    public StockDataDto getStockData(String accessToken, String code) {

        System.out.println("======== 모든 주식 데이터 리스트를 반환하는 로직 start======");
        System.out.println("");

        // 오늘 날짜를 문자 YYYYMMDD  포맷으로 가져온다.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        // 현재 날짜
        String today = sdf.format(c1.getTime());


        System.out.println("Today= " + today);

        String requestUrl = "https://openapivts.koreainvestment.com:29443/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice?FID_COND_MRKT_DIV_CODE=J&FID_INPUT_ISCD="+ code +"&FID_INPUT_DATE_1="+ today +"&FID_INPUT_DATE_2="+ today +"&FID_PERIOD_DIV_CODE=D&FID_ORG_ADJ_PRC=1";
        /*String requestParam = "{\n" +
                "    \"FID_COND_MRKT_DIV_CODE\": \"J\",\n" +
                "    \"FID_INPUT_ISCD\": \"005930\",\n" +
                "    \"FID_INPUT_DATE_1\": \"20220501\",\n" +
                "    \"FID_INPUT_DATE_2\": \"20220530\",\n" +
                "    \"FID_PERIOD_DIV_CODE\": \"D\",\n" +
                "    \"FID_ORG_ADJ_PRC\": \"1\"\n" +
                "}";*/

        HttpURLConnection conn = null;
        BufferedReader br = null;
        String responseData = "";

        StringBuffer sb = new StringBuffer();
        String returnData = "";

        try {
            URL url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();

            // Set the request method and content type
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type", "application/json; utf8");
            conn.setRequestProperty("authorization", "Bearer " + accessToken);
            conn.setRequestProperty("appkey", appkey);
            conn.setRequestProperty("appsecret", appsecret);
            conn.setRequestProperty("tr_id", "FHKST03010100");
            conn.setRequestProperty("custtype", "P");


            // Allow output for POST data
            conn.setDoOutput(true);

            // Http POST 요청을 통해 서버에 데이터를 전송합니다.
            try (OutputStream os = conn.getOutputStream()) {  // 객체 생성
                //byte requestData[] = requestParam.getBytes("utf-8");
                //System.out.println("requestData : " + requestData);
                //os.write(requestData);
                os.flush();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            conn.connect();
            System.out.println("http 요청 방식" + "POST BODY JSON");
            System.out.println("http 요청 타입" + "application/json");
            System.out.println("http 요청 주소" + requestUrl);
            System.out.println("");

            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            System.out.println("버퍼 처리 정상!");
        } catch (IOException e){
            e.printStackTrace();
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            System.out.println("예외가 발생하여 conn 에러스트림처리함.");
        } finally {
            try {
                sb = new StringBuffer();
                responseData = br.readLine();

                System.out.println();
                System.out.println("br = " + br);
                System.out.println("sb = " + sb);
                System.out.println();

                returnData = sb.toString();
                String responseCode = String.valueOf(conn.getResponseCode());
                System.out.println("http 응답 코드 : " + responseCode);
                System.out.println("http 응답 데이터 : " + responseData);

                // JSON 형식의 응답 데이터를 파싱
                JSONObject jsonResponse = new JSONObject(responseData);
                String successedCode = jsonResponse.optString("rt_cd", "");
                responseCode = jsonResponse.optString("msg_cd", "");
                String responseMessage = jsonResponse.optString("msg1", "");
                // 주식 상세 정보내용을 객체로 받아옴
                JSONObject stockData = jsonResponse.getJSONObject("output1");

                // 주식 상세 정보를 하나씩 문자열로 저장

                // 주식 종목 이름
                String corporationName = stockData.optString("hts_kor_isnm", "");
                // 전일 대비 가격
                String previousComparePrice = stockData.optString("prdy_vrss", "");
                // 전일 대비율
                String previousCompareRate = stockData.optString("prdy_ctrt", "");
                // 주식 현재 가격
                String stockPresentPrice = stockData.optString("stck_prpr", "");

                // 위에서 문자열로 받은 데이터를 이용해 주식 상세정보 Dto 객체 설정
                StockDataDto stockDataDto = new StockDataDto(corporationName, previousComparePrice, previousCompareRate, stockPresentPrice);

                System.out.println("successedCode = " + successedCode);
                System.out.println("responseCode = " + responseCode);
                System.out.println("responseMessage = " + responseMessage);
                System.out.println("stockData = " + stockDataDto);

                if (br != null){
                    br.close();
                    // StockController에 StockChartDto형식으로 반환
                    return stockDataDto;
                }
            } catch (IOException e) {
                throw new RuntimeException("API 응답을 읽는데 실패했습니댜.",e);
            }
        }
        return null;
    }

    // 모든 주식에 대한 정보를 반환
    public List<StockDataDto> getAllStockData(String accessToken) {
        List<StockDataDto> stockDataList = new ArrayList<>();
        List<Corporation> corporations = corporationRepository.findAll();

        for (int i=0; i<corporations.size(); i++) {
            stockDataList.add(getStockData(accessToken, corporations.get(i).getCode()));
        }

        return stockDataList;
    }

    // 주식 리스트 페이지에서 내 주식 보유 현황에 대한 데이터를 만들어 반환
    public MyStockInfoDto getMyStockInfo(Long userId, List<StockDataDto> stockDataList) {

        // 유저 정보 조회
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new NoSuchElementException("해당 유저에 대한 정보가 없습니다.")
        );

        // user 조회가 잘 되는지 체크해봐야함.
        List<CurrentStock> currentStockList= currentStockRepository.findByUser(user).orElseGet(
                () -> null
        );

        // 내가 가진 주식이 없다면 null 반환
        if (currentStockList == null) {
            return null;
        }
        int totalStockValue = 0;    // 총 주식 가치
        int totalChangeStockValue = 0;   // 총 주식 평가손익
        double totalChangeStockRate = 0;    // 수익률
        Long availablePoint = 0L;     // 사용 가능한 포인트
        int totalBuyStockPoint = 0;     // 총 주식 매수 포인트

        for(int i = 0; i<currentStockList.size(); i++) {
            int stockPresentValue = 0;
            for (int j = 0; j<stockDataList.size(); j++) {
                if (currentStockList.get(i).getCorporation().getCorporationName().equals(stockDataList.get(j).getName())) {
                    stockPresentValue = Integer.parseInt(stockDataList.get(j).getStockPresentPrice());
                    break;
                }
            }
            totalStockValue += currentStockList.get(i).getStockHoldingCount()*stockPresentValue;
            totalBuyStockPoint += currentStockList.get(i).getTotalPrice();
        }
        totalChangeStockValue = totalStockValue - totalBuyStockPoint;
        System.out.println("총 주식의 가치 = " + totalStockValue + "  총 주식 구매 가격 = " + totalBuyStockPoint);
        totalChangeStockRate = ((double)totalChangeStockValue/totalBuyStockPoint)*100;

        Point point = pointRepository.findByUser(user).orElseThrow(
                () -> new NoSuchElementException("해당 유저의 포인트에 대한 정보가 없습니다.")
        );
        availablePoint = point.getHoldingPoint();

        MyStockInfoDto myStockInfoDto = new MyStockInfoDto(totalStockValue, totalChangeStockValue, totalChangeStockRate, availablePoint, totalBuyStockPoint);

        return myStockInfoDto;
    }

    public void buyStock(User user, StockBuyDto stockBuyDto) {

        // 주식 회사 정보를 가져온다.
        Corporation corporation = corporationRepository.findByCorporationName(stockBuyDto.getName()).orElseThrow(
                () -> new NoSuchElementException("해당 주식 회사가 존재하지 않습니다.")
        );
        // 주식 거래 내역 테이블에 매수 내역 추가
        StockTradeHistory stockTradeHistory = new StockTradeHistory(user,corporation, stockBuyDto);
        stockTradeHistoryRepository.save(stockTradeHistory);
        // 이번에 구매한 주식의 총 가격
        int addPrice = stockBuyDto.getStockPresentPrice()* stockBuyDto.getBuyAmount();
        // 유저가 해당 주식을 구매한 적인 있는지 확인 (구매한 적 없다면 null이 반환)
        CurrentStock currentStock = currentStockRepository.findByCorporationAndUser(corporation, user).orElseGet(
                () -> null);
        // 사용자의 현재 보유 주식 정보 추가 (해당 주식이 첫 구매일 때)
        if (currentStock == null) {
            CurrentStock addCurrentStock = new CurrentStock(user, corporation, stockBuyDto.getStockPresentPrice(), addPrice, stockBuyDto.getBuyAmount());
            currentStockRepository.save(addCurrentStock);
        }
        // (해당 주식을 이전에 구매한 적이 있을 때)
        else {
            currentStock.buyUpdate(addPrice, stockBuyDto.getBuyAmount());
            currentStockRepository.save(currentStock);
        }
        // 학생 포인트 테이블에서 보유한 포인트 현황 업데이트
        Point point = pointRepository.findByUser(user).orElseThrow(
                () -> new NoSuchElementException("해당 유저에 대한 포인트 데이터가 없습니다.")
        );
        point.updateHoldingPoint(point.getHoldingPoint() - addPrice);
        pointRepository.save(point);

        System.out.println("매수 정보" + point);
    }

    public String sellStock(User user, StockSellDto stockSellDto) {
        // 주식 회사 정보를 가져온다.
        Corporation corporation = corporationRepository.findByCorporationName(stockSellDto.getName()).orElseThrow(
                () -> new NoSuchElementException("해당 주식에 대한 정보가 없습니다.")
        );
        // 이번에 매도한 주식의 총 가격
        int addPrice = stockSellDto.getStockPresentPrice()* stockSellDto.getSellAmount();
        // 유저가 보유한 해당 주식 정보 가져오기
        CurrentStock currentStock = currentStockRepository.findByCorporationAndUser(corporation, user).orElseGet(
                () -> null);
        if (currentStock == null) {
            return "보유 주식 없음";
        }
        // 파는 주식의 개수가 보유한 주식의 개수보다 크면 return "매도개수초과"
        if (currentStock.getStockHoldingCount() < stockSellDto.getSellAmount()) {
            return "매도 개수 초과";
        }
        // 매도에 성공했을 경우
        // 주식 거래 내역 테이블에 매도 내역 추가
        StockTradeHistory stockTradeHistory = new StockTradeHistory(user,corporation, stockSellDto);
        stockTradeHistoryRepository.save(stockTradeHistory);
        // 주식 매도 개수가 주식 보유 개수와 같을때, 해당 주식 보유 정보 삭제
        if (currentStock.getStockHoldingCount() == stockSellDto.getSellAmount()) {
            currentStockRepository.delete(currentStock);
        }
        // 주식 매도 개수가 주식 보유 개수보다 적을때, 해당 주식 보유 정보 업데이트
        else {
            currentStock.sellUpdate(stockSellDto.getSellAmount());
            currentStockRepository.save(currentStock);
        }
        // 학생 포인트 테이블에서 보유한 포인트 현황 업데이트
        Point point = pointRepository.findByUser(user).orElseThrow(
                () -> new NoSuchElementException("해당 유저에 대한 포인트 데이터가 없습니다.")
        );
        point.updateHoldingPoint(point.getHoldingPoint() + addPrice);
        pointRepository.save(point);

        return "매도 성공";
    }

}