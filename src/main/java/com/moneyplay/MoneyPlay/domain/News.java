package com.moneyplay.MoneyPlay.domain;

public class News {
    // 기사 썸네일 이미지 경로
    String news_imageUrl;
    // 기사 제목
    String news_title;
    // 기사 내용 (html 코드 포함)
    String news_content;
    // 기사 번호 (어린이 경제신문에서 관리하는 인덱스)
    // 이 인덱스를 통해 기사 상세 페이지를 접근
    Long news_index;

    public String getNews_imageUrl() {
        return news_imageUrl;
    }

    public void setNews_imageUrl(String news_imageUrl) {
        this.news_imageUrl = news_imageUrl;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public Long getNews_index() {
        return news_index;
    }

    public void setNews_index(Long news_index) {
        this.news_index = news_index;
    }
}
