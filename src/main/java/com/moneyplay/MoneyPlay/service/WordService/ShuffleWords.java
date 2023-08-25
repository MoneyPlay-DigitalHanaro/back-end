package com.moneyplay.MoneyPlay.service.WordService;

import com.moneyplay.MoneyPlay.domain.Word;
import com.moneyplay.MoneyPlay.domain.WordToday;
import com.moneyplay.MoneyPlay.repository.WordRepository;
import com.moneyplay.MoneyPlay.repository.WordTodayRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class ShuffleWords {

    final WordTodayRepository wordTodayRepository;
    final WordRepository wordRepository;

    public void ShuffleWord() {

        // 임의로 3개 골라서 DB에 저장하기 : 스케쥴링 사용하기

        wordTodayRepository.deleteAll();

        List<Word> words = wordRepository.findAll();
        Collections.shuffle(words);

        for (int i = 0; i < 3; i++) {
            WordToday wordToday = new WordToday();
            wordToday.setWordTodayName(words.get(i).getWordName());
            wordToday.setTodaycontent(words.get(i).getContent());

            wordTodayRepository.save(wordToday);
        }



    }
}
