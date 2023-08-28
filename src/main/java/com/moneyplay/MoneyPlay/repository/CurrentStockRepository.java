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
<<<<<<< HEAD
=======

>>>>>>> 1b245fd90b881b754493d7ab9a5926f2c32bc4a9
}
