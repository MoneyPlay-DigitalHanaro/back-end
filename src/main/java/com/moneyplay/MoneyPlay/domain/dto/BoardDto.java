package com.moneyplay.MoneyPlay.domain.dto;

import lombok.Data;

@Data

public class BoardDto {

    private Long BoardId;
    private String studentName;
    private String message;


    public BoardDto(Long boardId, String studentName, String message) {
        this.BoardId = boardId;
        this.studentName = studentName;
        this.message = message;
    }
}
