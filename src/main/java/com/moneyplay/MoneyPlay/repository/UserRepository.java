package com.moneyplay.MoneyPlay.repository;

import com.moneyplay.MoneyPlay.domain.ClassRoom;
import com.moneyplay.MoneyPlay.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByuserId(Long UserId);
    List<User> findByClassRoom(ClassRoom classRoom);

}
