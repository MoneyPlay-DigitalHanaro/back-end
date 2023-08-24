package com.moneyplay.MoneyPlay.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
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
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BoardController {

    final UserRepository userRepository;
    final ClassRoomRepository classRoomRepository;
    final BoardRepository boardRepository;

    // 게시판 화면 입장 시
    @GetMapping("/board")
    public List<BoardDto> Entrance(@RequestHeader("Authorization") String token2){

        // 토큰을 이용해서 학생 고유 키, 교실 고유키를 받야야됌(받았다고 가정함)

        String token = token2.substring(7);

        DecodedJWT decodedJWT = JWT.decode(token);
        Long id = decodedJWT.getClaim("id").asLong();

        User user = userRepository.findByuserId(id);
        ClassRoom classRooms = user.getClassRoom();


        // 교실 고유키 찾기
        ClassRoom classRoom = classRoomRepository.findByclassRoomId(classRooms.getClassRoomId());

        System.out.println(classRooms.getClassRoomId());

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
    public List<BoardDto> write(@RequestBody Map<String, String> requestBody,@RequestHeader("Authorization") String token2){

        String token = token2.substring(7);

        DecodedJWT decodedJWT = JWT.decode(token);
        Long id = decodedJWT.getClaim("id").asLong();

        User user = userRepository.findByuserId(id);
        ClassRoom classRooms = user.getClassRoom();

        String message = requestBody.get("message");

        ClassRoom classRoom = classRoomRepository.findByclassRoomId(classRooms.getClassRoomId());

        Board board = new Board(user,classRoom,message);

        boardRepository.save(board);

        List<Board> boards = boardRepository.findByclassRoom(classRoom);

        List<BoardDto> returnBoard = new ArrayList<>();

        for(int i=0; i<boards.size(); i++){

            BoardDto boardDto = new BoardDto(boards.get(i).getBoardId(),boards.get(i).getUser().getStudentName(),boards.get(i).getMessage());

            returnBoard.add(boardDto);
        }

        return returnBoard;
    }


}
