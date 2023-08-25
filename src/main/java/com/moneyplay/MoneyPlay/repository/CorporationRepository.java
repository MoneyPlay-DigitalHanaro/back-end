package com.moneyplay.MoneyPlay.repository;

import com.moneyplay.MoneyPlay.domain.Corporation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CorporationRepository extends JpaRepository<Corporation,Long> {

    Optional<Corporation> findByCorporationName(String corporationName);

    List<Corporation> findAll();
}
