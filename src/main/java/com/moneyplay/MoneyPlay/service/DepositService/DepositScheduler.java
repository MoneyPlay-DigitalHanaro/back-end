package com.moneyplay.MoneyPlay.service.DepositService;


import com.moneyplay.MoneyPlay.domain.Deposit;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.repository.DepositRepository.DepositRepository;
import com.moneyplay.MoneyPlay.repository.DepositRepository.DepositTypeRepository;

import com.moneyplay.MoneyPlay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
@Transactional
public class DepositScheduler {

    final DepositRepository depositRepository;
    final DepositTypeRepository depositTypeRepository;
    final UserRepository userRepository;


    @Scheduled(cron = "0 0 0 * * *")

//     @Scheduled(fixedRate = 5000) // 5초마다 실행
    public void Scheduler() {

        // 매일 정각에 만기일인지 체크

        LocalDate currentDate = LocalDate.now();
        List<Deposit> deposits = depositRepository.findAll();

        // 오늘 날짜

        int todayYear = currentDate.getYear();
        int todayMonth = currentDate.getMonthValue();
        int todayDay = currentDate.getDayOfMonth();



        for(int i=0; i<deposits.size(); i++){

            // user 정보 가져오기

            User user = deposits.get(i).getUser();

            // 적금 시작 날짜
//
//            int startYear = deposits.get(i).getStartDate().getYear();
//            int startMonth = deposits.get(i).getStartDate().getMonthValue();
//            int startDay = deposits.get(i).getStartDate().getDayOfMonth();


            // 적금 만기 날짜

            int endYear = deposits.get(i).getEndDate().getYear();
            int endMonth = deposits.get(i).getEndDate().getMonthValue();
            int endDay = deposits.get(i).getEndDate().getDayOfMonth();


            // 오늘이 만기일이라면


            if(todayYear == endYear && todayMonth == endMonth && todayDay == endDay){

                    // 원금 + 원금*(0.5+주) 지급

                    user.getPoint().setHoldingPoint((long) (

                            user.getPoint().getHoldingPoint()+
                            deposits.get(i).getDepositAmount()+
                            deposits.get(i).getDepositAmount()*(0.5+(deposits.get(i).getWeek()-4)*0.1)/100
                    ));

                    // 적금 금액 감소시키기

                    user.getPoint().setSavingPoint(user.getPoint().getSavingPoint()-deposits.get(i).getDepositAmount());

                    userRepository.save(user);
                    depositRepository.delete(deposits.get(i));
                }


            }

        }

    }


