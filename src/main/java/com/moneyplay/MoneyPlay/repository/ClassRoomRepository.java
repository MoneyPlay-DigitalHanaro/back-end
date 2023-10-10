package com.moneyplay.MoneyPlay.repository;

import com.moneyplay.MoneyPlay.domain.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    ClassRoom findByclassRoomId(Long classRoomId);

    Optional<ClassRoom> findByStudentGradeAndStudentClass(int studentGrade, int studentClass);
}
