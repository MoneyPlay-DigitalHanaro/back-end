package com.moneyplay.MoneyPlay.domain.dto;

import com.moneyplay.MoneyPlay.domain.ClassRoom;
import com.moneyplay.MoneyPlay.domain.School;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddInfoDto {
    private int studentGrade;
    private int studentClass;
    private String schoolName;
    private String email;
    private boolean isTeacher;
    private int studentNumber;
    private String studentProfile;
    private String studentName;
    private String image;
    private String nickname;
    private Long kakao_id;
    private String myRole;

}
