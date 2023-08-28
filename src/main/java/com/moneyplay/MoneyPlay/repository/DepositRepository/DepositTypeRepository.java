package com.moneyplay.MoneyPlay.repository.DepositRepository;

import com.moneyplay.MoneyPlay.domain.DepositType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositTypeRepository extends JpaRepository<DepositType,Long> {
<<<<<<< HEAD
=======

    DepositType findByDepositTypeId(Long id);

>>>>>>> 1b245fd90b881b754493d7ab9a5926f2c32bc4a9
}
