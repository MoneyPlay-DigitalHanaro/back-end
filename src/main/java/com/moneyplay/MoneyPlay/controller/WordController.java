package com.moneyplay.MoneyPlay.controller;

import com.moneyplay.MoneyPlay.domain.WordToday;
import com.moneyplay.MoneyPlay.repository.WordRepository;
import com.moneyplay.MoneyPlay.repository.WordTodayRepository;
import com.moneyplay.MoneyPlay.service.WordService.CreateWords;
import com.moneyplay.MoneyPlay.service.WordService.ShuffleWords;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WordController {

    final WordTodayRepository wordTodayRepository;
    final WordRepository wordRepository;

    @GetMapping("/word")
    public List<WordToday> word(){

        // CreateWords 실행해서 단어 DB에 저장하기 : 전체 단어 생성 필요할 때 필요할 때 주석 풀어주기

//         CreateWords createWords = new CreateWords(wordRepository);
//         createWords.CreateWords();


        List<WordToday> words = wordTodayRepository.findAll();

        System.out.println(words.size());

        for(int i=0;i<words.size();i++){
            System.out.println(words.get(i));
        }

        return words;

    }
}
