package com.moneyplay.MoneyPlay.controller;

import com.moneyplay.MoneyPlay.domain.News;
import com.moneyplay.MoneyPlay.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService){
        this.newsService = newsService;
    }


    @GetMapping
    public ResponseEntity<?> getNewsList() throws Exception {
        try {
            List<News> newsList = newsService.getNewsData();
            return new ResponseEntity<>(newsList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/detail")
    // 인덱스를 파라미터로 받아서 뉴스 상세 페이지에 접근
    public ResponseEntity<?> getNewsDetailPage(@RequestParam Long index) throws Exception {
        try {
            News newsDetail = newsService.getNewsDetail(index);
            return new ResponseEntity<>(newsDetail, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
