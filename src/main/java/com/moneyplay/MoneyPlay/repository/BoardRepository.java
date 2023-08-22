package com.moneyplay.MoneyPlay.repository;

import com.moneyplay.MoneyPlay.domain.Board;
import com.moneyplay.MoneyPlay.domain.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    List<Board> findByclassRoom(ClassRoom classRoom);
}
