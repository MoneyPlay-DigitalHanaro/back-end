package com.moneyplay.MoneyPlay.repository;

import com.moneyplay.MoneyPlay.domain.Point;
import com.moneyplay.MoneyPlay.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    Optional<Point> findByUser(User user);
}
