package com.moneyplay.MoneyPlay.repository;

import com.moneyplay.MoneyPlay.domain.Corporation;
import com.moneyplay.MoneyPlay.domain.CurrentStock;
import com.moneyplay.MoneyPlay.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrentStockRepository extends JpaRepository<CurrentStock,Long> {
    Optional<CurrentStock> findByCorporationAndUser(Corporation corporation, User user);
    Optional<List<CurrentStock>> findByUser(User user);
    Optional<List<CurrentStock>> findByUserId(Long userId);
}
