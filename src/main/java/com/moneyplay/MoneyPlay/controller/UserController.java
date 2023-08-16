package com.moneyplay.MoneyPlay.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moneyplay.MoneyPlay.domain.KakaoProfile;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.domain.dto.UserDetailDto;
import com.moneyplay.MoneyPlay.jwt.JwtProperties;
import com.moneyplay.MoneyPlay.jwt.OauthToken;
import com.moneyplay.MoneyPlay.jwt.TokenRequest;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import com.moneyplay.MoneyPlay.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/login/oauth2/code/kakao")
    public ResponseEntity Login(@RequestParam("code") String code) {

        OauthToken oauthToken = userService.getKakaoAccessToken(code);
        // 발급 받은 accessToken 으로 카카오 회원 정보 DB 저장 후 JWT 를 생성
        KakaoProfile profile = userService.findProfile(oauthToken.getAccess_token());

        String jwtToken = userService.saveUserAndGetToken(oauthToken.getAccess_token());
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, jwtToken);   // 헤더 prefix 뺐음 추가해야할듯?
        return ResponseEntity.ok().headers(headers).body("success");


    }
    @PostMapping("/api/register/kakao")
    public ResponseEntity<?> register(@RequestBody User user) {
        System.out.println(user);
        System.out.println(user.getNickname());
        userRepository.save(user);
        String jwtToken = userService.createToken(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, jwtToken);
        return ResponseEntity.ok().headers(headers).body("success");
    }

    @GetMapping("/auth/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        try {
            User user = userService.getUser(request);
            UserDetailDto userDetail = userService.getUserDetail(user);
            return new ResponseEntity<>(userDetail, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/decodeToken")
    public String decodeToken(@RequestBody TokenRequest tokenRequest) {
        String token = tokenRequest.getToken();

        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            String username = decodedJWT.getClaim("nickname").asString();
            String json = "{ \"username\": \"" + username + "\" }";
            return json;
        } catch (Exception e) {
            return "{ \"error\": \"Token invalid\" }";
        }
    }


}
