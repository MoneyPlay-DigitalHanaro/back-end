/*
package com.moneyplay.MoneyPlay.service.SchdulerService;

import com.moneyplay.MoneyPlay.domain.CurrentStock;
import com.moneyplay.MoneyPlay.domain.Deposit;
import com.moneyplay.MoneyPlay.domain.Point;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.domain.dto.*;
import com.moneyplay.MoneyPlay.repository.CurrentStockRepository;
import com.moneyplay.MoneyPlay.repository.DepositRepository.DepositRepository;
import com.moneyplay.MoneyPlay.repository.PointRepository;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import com.moneyplay.MoneyPlay.service.MyPageService;
import com.moneyplay.MoneyPlay.service.StockService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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

@Component
@RequiredArgsConstructor
public class UserPointUpdateScheduler {


    private final UserRepository userRepository;

    private final CurrentStockRepository currentStockRepository;

    private final DepositRepository depositRepository;

    private final PointRepository pointRepository;

    @Scheduled(cron = "0 * * * * *")
    public void userPointScheduler() {
        List<User> userList = userRepository.findAll();
        System.out.println("========================= 유저 포인트 업데이트 스케줄러 시작 =========================");
        System.out.println("API token 접근 직전!!!");
        // 한국투자증권 open api 에서 접근 토큰 발급
        StockAPITokenDto stockAPITokenDto = getApiToken();
        System.out.println("API token 받아옴!");

        for (int i=0; i<userList.size(); i++) {
            // 유저가 가지고 있는 주식에 대한 데이터 리스트
            List<MyStockDto> myStockDtoList = null;
            // 유저가 가지고 있는 예금에 대한 데이터
            MyDepositDto myDepositDto = null;
            // 유저의 포인트 상세 데이터
            MyPointDto myPointDto = null;


            // 유저의 보유 주식 정보를 가져온다. (보유 주식이 없으면 null)
            List<CurrentStock> currentStockList = currentStockRepository.findByUser(userList.get(i)).orElseThrow(null);
            // 유저의 보유 주식 관련 데이터와 한국투자증권 open api 에서 내가 지정한 모든 주식 리스트 정보 가져오기
            List<StockDataDto> userStockDataList = new ArrayList<>();

            System.out.println("유저의 주식 리스트 확인 직전!!!  보유 주식 수 = " + currentStockList.size());
            if (currentStockList == null) {
                userStockDataList = null;
                myStockDtoList = null;
            }
            else {
                System.out.println("주식데이터 만드는 for문 입장전!!");
                for (int j = 0; j < currentStockList.size(); j++) {
                    userStockDataList.add(getStockData(stockAPITokenDto.getAccessToken(), currentStockList.get(j).getCorporation().getCode()));
                }

                System.out.println("주식데이터 만드는 for문 입장후!!");

                // 유저의 주식 정보를 myPage 표현 양식에 맞게 가져온다.
                myStockDtoList = findUserStock(userList.get(i), currentStockList, userStockDataList);
                System.out.println("!!유저의 주식 수= " + myStockDtoList.size());
            }

            System.out.println("유저의 적금 데이터 확인 직전!!!");
            // 유저의 적금 정보를 가져온다.
            Deposit deposit = depositRepository.findByUser(userList.get(i)).orElse(null);
            System.out.println("!!예금=  " + deposit.getDepositType().getDepositName());
            if (deposit != null) {
                myDepositDto = new MyDepositDto(deposit);
                System.out.println("적금 이름= " + myDepositDto.getDepositType().getDepositName());
            }
            else {
                System.out.println("예금 정보= "+ deposit);
            }

            System.out.println("예금 Dto 정보= "+ myDepositDto);
            // 유저의 포인트 정보를 가져온다.
            myPointDto = findUserPoint(userList.get(i), myStockDtoList, myDepositDto);

            System.out.println("유저의 포인트 총합= " + myPointDto.getTotalPoint());
        }

    }

    public StockAPITokenDto getApiToken(){
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
    }

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
        */
/*String requestParam = "{\n" +
                "    \"FID_COND_MRKT_DIV_CODE\": \"J\",\n" +
                "    \"FID_INPUT_ISCD\": \"005930\",\n" +
                "    \"FID_INPUT_DATE_1\": \"20220501\",\n" +
                "    \"FID_INPUT_DATE_2\": \"20220530\",\n" +
                "    \"FID_PERIOD_DIV_CODE\": \"D\",\n" +
                "    \"FID_ORG_ADJ_PRC\": \"1\"\n" +
                "}";*//*


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
            conn.setRequestProperty("appkey", "PSOcm47UhZZjH5638ilgaAcsQU8mKHrxtnza");
            conn.setRequestProperty("appsecret", "QJFlWFe02Ae+pKF2B7AOJwN9IsqyPSXR9ozWn45Q55/nq2awQ4Jt18G9/0zq4+pm+rZkZmXli4qTUeTbYTQg8nZc40K95RlyJHah4PyRFzuwv6enRZHjAT9xB7Psvliu4eutG8lLBqiEsQJKXbWEVEQDuvV9JIK3q5BIHEu13cYgCEjRS9w=");
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


    public List<MyStockDto> findUserStock(User user, List<CurrentStock> currentStockList, List<StockDataDto> userStockDataList) {

        // MyStockDto
        String name;                // 주식 이름
        int presentPrice;           // 현재 가격
        int hodingStockCount;       // 가지고 있는 주식 수
        Long totalStockValue;       // 가지고 있는 주식의 총 가치
        Long changeStockValue;      // 가지고 있는 주식의 총 수익금액
        double changeStockRate;     // 가지고 있는 주식의 총 수익률
        int totalBuyStockPoint;     // 해당 주식을 구매하는데 사용한 총 포인트

        List<MyStockDto> myStockDtoList = new ArrayList<>();

        for (int i=0; i<userStockDataList.size(); i++) {
            name = userStockDataList.get(i).getName();
            presentPrice = Integer.parseInt(userStockDataList.get(i).getStockPresentPrice());
            hodingStockCount = currentStockList.get(i).getStockHoldingCount();
            totalStockValue = presentPrice*hodingStockCount*1L;
            totalBuyStockPoint = currentStockList.get(i).getTotalPrice();
            changeStockValue = totalStockValue - totalBuyStockPoint;
            changeStockRate = ((double)changeStockValue/totalBuyStockPoint)*100;
            MyStockDto myStockDto = new MyStockDto(name, presentPrice, hodingStockCount, totalStockValue, changeStockValue, changeStockRate);

            myStockDtoList.add(myStockDto);
        }



        return myStockDtoList;
    }

    public MyPointDto findUserPoint(User user, List<MyStockDto> myStockDtoList, MyDepositDto myDepositDto) {

        // 유저의 포인트 정보
        Long totalPoint;     // 총 포인트 가치
        Long changePointValue = 0L;       // 수익 금액
        double changePointRate;     // 수익률
        Long availablePoint;     // 사용가능한 포인트
        Long totalStockPoint = 0L;       // 총 주식 포인트
        Long totalDepositPoint = 0L;  //총 적금 포인트

        for (int i = 0; i<myStockDtoList.size(); i++) {
            changePointValue += myStockDtoList.get(i).getChangeStockValue();
            totalStockPoint += myStockDtoList.get(i).getTotalStockValue();
        }

        System.out.println("수익 포인트= " + changePointValue + "  총 주식 포인트= " + totalStockPoint);

        if (myDepositDto != null)
            myDepositDto.getDepositAmount();

        totalPoint = totalDepositPoint + totalStockPoint;
        changePointRate = ((double)changePointValue/totalPoint)*100;
        availablePoint = user.getPoint().getHoldingPoint();
        if (myDepositDto != null) {
            changePointValue += myDepositDto.getInterestAmount();
            totalDepositPoint = myDepositDto.getDepositAmount() + myDepositDto.getInterestAmount();
        }
        else
            totalDepositPoint = 0L;

        // 유저의 총 주식 가치 포인트 저장
        saveUserStockPoint(user ,totalStockPoint);
        // 유저의 총 예금 가치 포인트 저장
        saveUserDepositPoint(user, totalDepositPoint);

        MyPointDto myPointDto = new MyPointDto(totalPoint, changePointValue, changePointRate,availablePoint,totalStockPoint, totalDepositPoint);

        return myPointDto;
    }

    public void saveUserStockPoint(User user, Long totalStockPoint) {
        Point point = pointRepository.findByUser(user).orElseThrow(
                () -> new NoSuchElementException("해당 유저의 포인트 정보가 존재하지 않습니다.")
        );

        point.updateStockPoint(totalStockPoint);
        pointRepository.save(point);
    }

    public void saveUserDepositPoint(User user, Long totalDepositPoint) {
        Point point = pointRepository.findByUser(user).orElseThrow(
                () -> new NoSuchElementException("해당 유저의 포인트 정보가 존재하지 않습니다.")
        );

        point.updateDepositPoint(totalDepositPoint);
        pointRepository.save(point);
    }

}
*/
