package com.moneyplay.MoneyPlay.repository;

import com.moneyplay.MoneyPlay.domain.CurrentStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentStockRepository extends JpaRepository<CurrentStock,Long> {

}
