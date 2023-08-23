package com.moneyplay.MoneyPlay.repository.DailyRepository;

import com.moneyplay.MoneyPlay.domain.Board;
import com.moneyplay.MoneyPlay.domain.ClassRoom;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.domain.UserDailyPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDailyPointRepository extends JpaRepository<UserDailyPoint,Long> {

}
