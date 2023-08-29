package com.moneyplay.MoneyPlay.service.SchdulerService;

import com.moneyplay.MoneyPlay.domain.ClassDailyPoint;
import com.moneyplay.MoneyPlay.domain.ClassRoom;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.domain.UserDailyPoint;
import com.moneyplay.MoneyPlay.repository.ClassRoomRepository;
import com.moneyplay.MoneyPlay.repository.DailyRepository.ClassDailyPointRepository;
import com.moneyplay.MoneyPlay.repository.DailyRepository.UserDailyPointRepository;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PointUpdateScheduler {

    final UserRepository userRepository;
    final ClassRoomRepository classRoomRepository;
    final ClassDailyPointRepository classDailyPointRepository;
    final UserDailyPointRepository userDailyPointRepository;

    // 날짜 확인 후 자정에 스케쥴러 실행


    // @Scheduled(fixedRate = 5000) // 5초마다 실행
    @Scheduled(cron = "0 0 0 * * *")
    public void Scheduler() {


        // ClassDailyPoint 업데이트

        List<ClassRoom> classRooms = classRoomRepository.findAll();
        List<User> users = userRepository.findAll();

        for(int i=0; i<classRooms.size(); i++){

            LocalDate localDate = LocalDate.now();
            Long ClassId = classRooms.get(i).getClassRoomId();
            Long total_point = 0L;

            for(int j=0; j<users.size(); j++){

                if(users.get(j).getClassRoom().getClassRoomId() == ClassId){
                    total_point += (long) (users.get(j).getPoint().getHoldingPoint()+
                            users.get(j).getPoint().getSavingPoint()+users.get(j).getPoint().getStockPoint());
                }

            }

            ClassDailyPoint classDailyPoint = new ClassDailyPoint(classRooms.get(i), localDate, total_point);
            classDailyPointRepository.save(classDailyPoint);
        }


        // UserDailyPoint 업데이트

        for(int i=0; i<users.size(); i++){

            // 현재 총 합 구하기
            Long current_total = (long) (users.get(i).getPoint().getHoldingPoint()+
                                users.get(i).getPoint().getSavingPoint()+users.get(i).getPoint().getStockPoint());

            LocalDate localDate = LocalDate.now();

            UserDailyPoint userDailyPoint = new UserDailyPoint(users.get(i),users.get(i).getClassRoom(),localDate,current_total);

            users.get(i).setTotalHoldingPoint(current_total);

            userRepository.save(users.get(i));
            userDailyPointRepository.save(userDailyPoint);
        }


        for(int i=0; i<users.size(); i++){
            users.get(i).setTotalHoldingPoint(
                    users.get(i).getPoint().getHoldingPoint()+
                    users.get(i).getPoint().getStockPoint()+
                    users.get(i).getPoint().getSavingPoint()
            );

            userRepository.save(users.get(i));


        }


    }
}





