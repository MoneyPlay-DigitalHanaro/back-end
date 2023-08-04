package com.moneyplay.MoneyPlay.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.moneyplay.MoneyPlay.domain.dto.StockAPITokenDto;
import com.moneyplay.MoneyPlay.domain.dto.StockRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;


@Service
@RequiredArgsConstructor
public class StockService {


    ObjectMapper mapper;
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
                br.close();

                // JSON 형식의 응답 데이터를 파싱
                JSONObject jsonResponse = new JSONObject(response);
                String accessToken = jsonResponse.optString("access_token", "");
                String tokenType = jsonResponse.optString("token_type", "");
                int expiresIn = jsonResponse.optInt("expires_in", 0);
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

}
