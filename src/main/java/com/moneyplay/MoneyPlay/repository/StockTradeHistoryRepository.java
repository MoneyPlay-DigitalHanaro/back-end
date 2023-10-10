package com.moneyplay.MoneyPlay.repository;

import com.moneyplay.MoneyPlay.domain.StockTradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockTradeHistoryRepository extends JpaRepository<StockTradeHistory, Long> {

}
