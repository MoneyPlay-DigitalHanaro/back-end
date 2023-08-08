package com.moneyplay.MoneyPlay.domain.dto;

import com.moneyplay.MoneyPlay.domain.ClassRoom;
import com.moneyplay.MoneyPlay.domain.OauthEnums.Role;
import com.moneyplay.MoneyPlay.domain.School;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.repository.ClassRoomRepository;
import com.moneyplay.MoneyPlay.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;
public class UserDetailDto {


    private Long userId;
    private Long school_id;
    private Long classroom_id;
    private int studentNumber;
    private String studentName;
    private boolean isTeacher;
    private String studentProfile;

    //Oauth login
    private Long kakao_id;
    private String image;
    private String nickname;

    private String eMail;

    public UserDetailDto(User user) {
        this.userId = user.getUserId();
        this.kakao_id = user.getKakao_id();
        this.eMail = user.getEMail();
        this.nickname = user.getNickname();
        this.image = user.getImage();
        this.studentName=user.getStudentName();
        this.classroom_id= user.getClassRoom().getClassRoomId();
        this.school_id= user.getSchool().getSchoolId();

    }
}
