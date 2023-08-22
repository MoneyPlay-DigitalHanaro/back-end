package com.moneyplay.MoneyPlay.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moneyplay.MoneyPlay.domain.KakaoProfile;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.domain.dto.AddInfoDto;
import com.moneyplay.MoneyPlay.domain.dto.ApplicationResponse;
import com.moneyplay.MoneyPlay.domain.dto.UserDetailDto;
import com.moneyplay.MoneyPlay.jwt.*;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import com.moneyplay.MoneyPlay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/login/oauth2/code/kakao")
    public ApplicationResponse<String> Login(@RequestParam("code") String code) {
        User user = null;
        //카카오로부터 OauthToken 발급 받기!
        OauthToken oauthToken = userService.getKakaoAccessToken(code);
        KakaoProfile kakaoProfile = userService.findProfile(oauthToken.getAccess_token());
        Optional<User> optionalUser = userRepository.findByEmail(kakaoProfile.getKakao_account().email);

        //사용자가 현재있는지 이메일로 확인 (카카오 로그인 이니까)
        if (!optionalUser.isPresent()) {

            return ApplicationResponse.ok(ErrorCode.AUTH_USER_NOT_FOUND,
                    kakaoProfile.getKakao_account().email + kakaoProfile.getKakao_account().profile.nickname
                            + kakaoProfile.id + kakaoProfile.getProperties().thumbnail_image);
        }else {
            //발급 받은 OauthToken으로 카카오 회원 정보 DB저장하고 Jwt생성
            String jwtToken = userService.saveUserAndGetToken(oauthToken.getAccess_token());

            //ApplicationResponse로 반환시켜서 에러 코드 받기
            return ApplicationResponse.ok(ErrorCode.SUCCESS_CREATED, JwtProperties.TOKEN_PREFIX + jwtToken);

        }
    }


    @PostMapping("/api/register/kakao")
    public ResponseEntity<?> register(@RequestBody AddInfoDto addInfoDto) {
        User user = userService.convertToEntity(addInfoDto);
        userRepository.save(user);
        String jwtToken = userService.createToken(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
        return ResponseEntity.ok().headers(headers).body("success");
    }


    @GetMapping("/auth/me")
    public ApplicationResponse<UserDetailDto> getCurrentUser(@RequestBody HttpServletRequest request) {
        User user = userService.getUser(request);
        UserDetailDto userDetail = userService.getUserDetail(user);
        return ApplicationResponse.ok(ErrorCode.SUCCESS_OK, userDetail);
    }


    @PostMapping("/decodeToken")
    public String decodeToken(HttpServletRequest request) {
        // Authorization 헤더에서 토큰 값을 가져옵니다.
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return "{ \"error\": \"Bearer token missing\" }";        }
        String token = authHeader.substring(7);
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            String nickname = decodedJWT.getClaim("nickname").asString();
            String email = decodedJWT.getClaim("sub").asString();
            Long id = decodedJWT.getClaim("id").asLong();

            String json = "{ \"username\": \"" + nickname + "\", \"email\": \"" + email + "\", \"id\": \"" + id + "\" }";

            return json;
        } catch (Exception e) {
            return "{ \"error\": \"Token invalid\" }";
        }
    }


    @GetMapping("/logout/service")
    public ApplicationResponse<String> serviceLogout(@AuthenticationPrincipal PrincipalDetails principalDetails){
        // 서비스 로그아웃 (토큰 만료시키기)
        userService.serviceLogout(principalDetails);
        return ApplicationResponse.ok(ErrorCode.SUCCESS_OK, "로그아웃 되었습니다.");
    }


}
