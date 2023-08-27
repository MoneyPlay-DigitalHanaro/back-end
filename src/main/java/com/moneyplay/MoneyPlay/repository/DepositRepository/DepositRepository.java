package com.moneyplay.MoneyPlay.repository.DepositRepository;

import com.moneyplay.MoneyPlay.domain.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepositRepository extends JpaRepository<Deposit,Long> {

    Optional<Deposit> findByUserId(Long userId);
}
