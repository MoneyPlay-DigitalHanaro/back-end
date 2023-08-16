package com.moneyplay.MoneyPlay.repository;

import com.moneyplay.MoneyPlay.domain.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    ClassRoom findByclassRoomId(Long classRoomId);
}
