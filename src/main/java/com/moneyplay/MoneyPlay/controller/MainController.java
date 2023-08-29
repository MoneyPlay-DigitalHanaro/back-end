package com.moneyplay.MoneyPlay.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final UserRepository userRepository;

    public ResponseEntity<?> mainData(@RequestHeader("Authorization") String tokens) {
        // 헤더에서 토큰을 가져온다.
        String token =tokens.substring(7);
        // 토큰값을 decode하여 id값을 가져온다.
        DecodedJWT decodedJWT = JWT.decode(token);
        Long userId = decodedJWT.getClaim("id").asLong();

        User user = userRepository.findByuserId(userId);

        List<User> userList = userRepository.findAll();
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
