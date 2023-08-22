package com.moneyplay.MoneyPlay.controller;

import com.moneyplay.MoneyPlay.domain.Board;
import com.moneyplay.MoneyPlay.domain.ClassRoom;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.domain.dto.BoardDto;
import com.moneyplay.MoneyPlay.repository.BoardRepository;
import com.moneyplay.MoneyPlay.repository.ClassRoomRepository;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    final UserRepository userRepository;
    final ClassRoomRepository classRoomRepository;
    final BoardRepository boardRepository;

    // 게시판 화면 입장 시
    @GetMapping("/board")
    public List<BoardDto> Entrance(){

        // 토큰을 이용해서 학생 고유 키, 교실 고유키를 받야야됌(받았다고 가정함)

        Long userId = 1L;
        Long classId = 1L;

        // 교실 고유키 찾기
        ClassRoom classRoom = classRoomRepository.findByclassRoomId(classId);


        List<Board> boards = boardRepository.findByclassRoom(classRoom);

        List<BoardDto> returnBoard = new ArrayList<>();

        for(int i=0; i<boards.size(); i++){

            BoardDto boardDto = new BoardDto(boards.get(i).getBoardId(),boards.get(i).getUser().getStudentName(),boards.get(i).getMessage());

            returnBoard.add(boardDto);
        }


        return returnBoard;

    }



    // 글 작성 시
    @PostMapping("/board")
    public List<Board> write(@PathVariable String message){

        Long userId = 1L;
        Long classId = 1L;

        User user = userRepository.findByuserId(userId);
        ClassRoom classRoom = classRoomRepository.findByclassRoomId(classId);

        Board board = new Board(user,classRoom,message);

        boardRepository.save(board);

        return boardRepository.findByclassRoom(classRoom);
    }


}
