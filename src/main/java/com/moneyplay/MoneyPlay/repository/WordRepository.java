package com.moneyplay.MoneyPlay.repository;

import com.moneyplay.MoneyPlay.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word,Long> {
    Word findByWordId(Long wordId);
}
