package com.moneyplay.MoneyPlay.repository;

import com.moneyplay.MoneyPlay.domain.ClassRoom;
import com.moneyplay.MoneyPlay.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);



    Optional<User> findByUserId(Long Long);
    User findByuserId(Long UserId);

    List<User> findByClassRoom(ClassRoom classRoom);

}

