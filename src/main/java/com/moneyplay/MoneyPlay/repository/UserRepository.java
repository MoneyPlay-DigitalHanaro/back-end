package com.moneyplay.MoneyPlay.repository;

import com.moneyplay.MoneyPlay.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByeMail(String email);


    Optional<User> findByUserId(Long Long);
}
