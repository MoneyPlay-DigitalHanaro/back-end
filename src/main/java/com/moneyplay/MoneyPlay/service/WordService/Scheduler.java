package com.moneyplay.MoneyPlay.service.WordService;

import com.moneyplay.MoneyPlay.repository.WordRepository;
import com.moneyplay.MoneyPlay.repository.WordTodayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Scheduler {

    final WordTodayRepository wordTodayRepository;
    final WordRepository wordRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void Scheduler() {

        // 날짜 확인 후 자정에 스케쥴러 실행

        ShuffleWords shuffleWords = new ShuffleWords(wordTodayRepository, wordRepository);
        shuffleWords.ShuffleWord();
    }
}
