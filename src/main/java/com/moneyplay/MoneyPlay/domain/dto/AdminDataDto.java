package com.moneyplay.MoneyPlay.domain.dto;

import com.moneyplay.MoneyPlay.domain.ClassDailyPoint;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.domain.UserDailyPoint;
import lombok.Data;

import java.util.List;

@Data
public class AdminDataDto {

    private List<User> user;

    private List<UserDailyPoint> userDailyPoint;

    private List<ClassDailyPoint> classDailyPoint;


    public AdminDataDto(List<User> users, List<UserDailyPoint> userDailyPoints, List<ClassDailyPoint> classDailyPoints) {
        this.user = users;
        this.userDailyPoint = userDailyPoints;
        this.classDailyPoint = classDailyPoints;
    }
}
