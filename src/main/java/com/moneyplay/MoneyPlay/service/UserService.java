package com.moneyplay.MoneyPlay.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneyplay.MoneyPlay.domain.ClassRoom;
import com.moneyplay.MoneyPlay.domain.KakaoProfile;
import com.moneyplay.MoneyPlay.domain.OauthEnums.Role;
import com.moneyplay.MoneyPlay.domain.School;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.domain.dto.AddInfoDto;
import com.moneyplay.MoneyPlay.domain.dto.UserDetailDto;
import com.moneyplay.MoneyPlay.jwt.*;
import com.moneyplay.MoneyPlay.repository.ClassRoomRepository;
import com.moneyplay.MoneyPlay.repository.SchoolRepository;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {


    @Autowired
    private final UserRepository userRepository;

    @Autowired
    ClassRoomRepository classRoomRepository;
    @Autowired
    SchoolRepository schoolRepository;




    public OauthToken getKakaoAccessToken (String code) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "262778662e9437ec42d6cc9d231e88bc");
        params.add("redirect_uri", "http://localhost:3000/api/login/oauth2/code/kakao");
        params.add("code", code);
        params.add("client_secret", "vhJNa6nXjI0QFOAxpH2CkTtiOpd42LRb");

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oauthToken;
    }

    public KakaoProfile findProfile(String token) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        // Http 요청 (POST 방식) 후, response 변수에 응답을 받음
        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        System.out.println(kakaoProfileResponse.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;
    }


    public String saveUserAndGetToken(String token) {

        KakaoProfile profile = findProfile(token);

        System.out.println(profile);

        User user = null;

        try {
            user = userRepository.findByEmail(profile.getKakao_account().getEmail()).get();
        } catch (NoSuchElementException e) {

            Long classRoomId = 1L;
            Long schoolId = 1L;
            Boolean isTeacher = false;
            int studentNumber = 1;
            String studentProfile = "123";
            Long userId = 1L;
            String studentName = "사람1";

            ClassRoom classRoom = classRoomRepository.findByclassRoomId(classRoomId);
            School school = schoolRepository.findByschoolId(schoolId);

            user = User.builder()
                    .kakao_id(profile.getId())
                    .image(profile.getKakao_account().getProfile().getProfile_image_url())
                    .nickname(profile.getKakao_account().getProfile().getNickname())
                    .email(profile.getKakao_account().getEmail())
                    .myRole(Role.MEMBER)
                    .classRoom(classRoom)
                    .school(school)
                    .isTeacher(isTeacher)
                    .studentNumber(studentNumber)
                    .studentProfile(studentProfile)
                    .userId(userId)
                    .studentName(studentName)
                    .build();

            userRepository.save(user);
        }

        return createToken(user);
    }

    public String createToken(User user) {


        String jwtToken = JWT.create()

                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))

                .withClaim("id", user.getUserId())
                .withClaim("nickname", user.getNickname())

                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        System.out.println(JwtProperties.SECRET);

        return jwtToken;
    }
    public User getUser(HttpServletRequest request) {
        System.out.println(request);
        Long userId = (Long) request.getAttribute("id");
        System.out.println(userId+"3");

        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new NoSuchElementException("해당 사용자가 존재하지 않습니다.")
        );

        return user;
    }

    public UserDetailDto getUserDetail(User user) {
        return new UserDetailDto(user);
    }

    public User convertToEntity(AddInfoDto addInfoDto) {


        ClassRoom classRoom = classRoomRepository.findByStudentGradeAndStudentClass(addInfoDto.getStudentGrade(), addInfoDto.getStudentClass()).orElseThrow(
                () -> new NoSuchElementException("해당 학년, 반이 존재하지 않습니다.")
        );

        School school = schoolRepository.findBySchoolName(addInfoDto.getSchoolName()).orElseThrow(
                () -> new NoSuchElementException("해당 학교가 존재하지 않습니다.")
        );

        User user = new User();
        user = User.builder()
                .email(addInfoDto.getEmail())
                .image(addInfoDto.getImage())
                .nickname(addInfoDto.getNickname())
                .myRole(Role.MEMBER)
                .classRoom(classRoom)
                .school(school)
                .isTeacher(addInfoDto.isTeacher())
                .studentNumber(addInfoDto.getStudentNumber())
                .studentProfile(addInfoDto.getStudentProfile())
                .kakao_id(addInfoDto.getKakao_id())
                .studentName(addInfoDto.getStudentName())
                .build();

        return user;
    }


    public void serviceLogout(PrincipalDetails principalDetails){
        // 서비스 로그아웃
        OauthToken oauthToken = OauthTokenMap.getInstance().getOauthTokens().get(principalDetails.getUser().getUserId());
        System.out.println(oauthToken);

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        HttpEntity<MultiValueMap<String, String>> logoutRequest = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = rt.exchange(
                    "https://kapi.kakao.com/v1/user/logout",
                    HttpMethod.POST,
                    logoutRequest,
                    String.class
            );

            System.out.println("회원번호 " + response.getBody() + " 로그아웃");
            deleteOauthToken(principalDetails.getUser().getUserId());

        } catch (Exception e) {
            throw new CustomException(ErrorCode.AUTH_EXPIRED_ACCESS_TOKEN);
        }

    }

    public void deleteOauthToken(Long userId) {
        // OauthTokenMap에서 삭제
        OauthTokenMap.getInstance().getOauthTokens().remove(userId);
    }


}
