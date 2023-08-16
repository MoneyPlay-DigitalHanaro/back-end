package com.moneyplay.MoneyPlay.repository;

import com.moneyplay.MoneyPlay.domain.School;
import com.moneyplay.MoneyPlay.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School,Long> {

    School findByschoolId(Long schoolId);
}
