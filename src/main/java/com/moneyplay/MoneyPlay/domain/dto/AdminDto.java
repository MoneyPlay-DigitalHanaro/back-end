package com.moneyplay.MoneyPlay.domain.dto;

import java.util.List;

public class AdminDto {

    private List<Long> user;
    private int increase_point;

    public List<Long> getUser() {
        return user;
    }

    public void setUser(List<Long> user) {
        this.user = user;
    }

    public int getIncrease_point() {
        return increase_point;
    }

    public void setIncrease_point(int increase_point) {
        this.increase_point = increase_point;
    }
}

