package com.moneyplay.MoneyPlay.service;

import com.moneyplay.MoneyPlay.domain.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    // 어린이 경제신문 > 이야기 경제 > 생활경제 링크
    String News_URL = "https://www.econoi.com/news/articleList.html?sc_sub_section_code=S2N1&view_type=sm";

    // 뉴스 데이터 가져오기 (생활경제 1페이지에 있는 20개)
    public List<News> getNewsData() throws IOException {
        List<News> newsList = new ArrayList<>();
        // 웹 페이지의 GET 데이터 정보 가져오기
        Document document = Jsoup.connect(News_URL).get();
        // 뉴스 Elements만 가져오기
        Elements contents = document.select("ul li.items");

        // 뉴스 Elements 데이터를 순회
        for (Element content: contents) {
            News news = new News();
            // 뉴스 썸네일 이미지 url 추출 후 news 객체에 값 저장
            news.setNews_imageUrl(content.select(".thumb img").attr("abs:src"));
            // 뉴스 제목 추출 후 news 객체에 값 저장
            news.setNews_title(content.select(".titles a").text());
            // 뉴스 내용 추출 후 news 객체에 값 저장
            news.setNews_content(content.select(".lead a").text());

            // 뉴스 인덱스 추출하는 과정
            // 뉴스 상세페이지의 url을 추출
            String detail_url = content.select(".items .thumb").attr("href");
            // url을 '=' 기준으로 split 했을 때 1번째 인덱스의 값이 뉴스 인덱스
            // get 방식의 첫번째 파라미터이기 때문
            Long index = Long.parseLong(detail_url.split("=")[1]);
            // 뉴스 인덱스 news 객체에 값 저장
            news.setNews_index(index);

//            System.out.println("imageUrl : " + news.getNews_imageUrl());
//            System.out.println("title : " + news.getNews_title());
//            System.out.println("content : " + news.getNews_content());
//            System.out.println("index : " + news.getNews_index());

            newsList.add(news);
        }
        // 뉴스 리스트 반환
        return newsList;
    }

    // 뉴스 상세 페이지 조회
    public News getNewsDetail(Long index) throws IOException {
        // 뉴스 상세 페이지 url에 인덱스를 붙여 줌
        String News_DetailURL = "https://www.econoi.com/news/articleView.html?idxno=";
        News_DetailURL += Long.toString(index);

        // 뉴스 상세 페이지의 GET 데이터 정보 가져오기
        Document document = Jsoup.connect(News_DetailURL).get();

        News news = new News();
        // 뉴스 제목 추출 후 객체에 값 저장
        String title = document.select("h1").text();
        news.setNews_title(title);

        // 뉴스 영역 추출
        Elements contents = document.select(".article-body");
        // 뉴스 썸네일 이미지 url 추출 후 객체에 값 저장
        String imgUrl = contents.select("figure img").attr("abs:src");
        news.setNews_imageUrl(imgUrl);

        // 뉴스 본문 문단 추출
        Elements paragraphs = contents.select("#article-view-content-div");

        // 문단 순회하며 뉴스 내용 완성
        String tmpContent = "";
        for (Element pg : paragraphs) {
            // text 대신 toString 메서드 사용하여 html 태그가 포함되도록 함
            tmpContent += pg.toString();
        }

        // 뉴스 내용과 인덱스를 객체에 값 저장
        news.setNews_content(tmpContent);
        news.setNews_index(index);

//        System.out.println("imageUrl : " + news.getNews_imageUrl());
//        System.out.println("title : " + news.getNews_title());
//        System.out.println("content : " + news.getNews_content());
//        System.out.println("index : " + news.getNews_index());

        // 해당 뉴스 객체 반환
        return news;

    }
}
