package com.moneyplay.MoneyPlay.repository.DepositRepository;

import com.moneyplay.MoneyPlay.domain.Deposit;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit,Long> {
=======
import com.moneyplay.MoneyPlay.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepositRepository extends JpaRepository<Deposit,Long> {

    Optional<Deposit> findByUser(User user);
>>>>>>> 1b245fd90b881b754493d7ab9a5926f2c32bc4a9
}
