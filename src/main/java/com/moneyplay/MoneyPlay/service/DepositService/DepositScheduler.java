package com.moneyplay.MoneyPlay.service.DepositService;


import com.moneyplay.MoneyPlay.domain.Deposit;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.repository.DepositRepository.DepositRepository;
import com.moneyplay.MoneyPlay.repository.DepositRepository.DepositTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DepositScheduler {

    final DepositRepository depositRepository;
    final DepositTypeRepository depositTypeRepository;

    @Scheduled(cron = "0 0 0 * * *")
//@Scheduled(fixedRate = 5000) // 5초마다 실행
    public void Scheduler() {

        // 매일 정각에 만기가 되었는지, 이자 지급 일자 인지 체크

        LocalDate currentDate = LocalDate.now();
        List<Deposit> deposits = depositRepository.findAll();

        // 오늘 날짜

        int todayYear = currentDate.getYear();
        int todayMonth = currentDate.getMonthValue();
        int todayDay = currentDate.getDayOfMonth();
        DayOfWeek todayWeek = currentDate.getDayOfWeek(); // 오늘 요일


        for(int i=0; i<deposits.size(); i++){

            // user 정보 가져오기

            User user = deposits.get(i).getUser();

            // 적금 시작 날짜

            int startYear = deposits.get(i).getStartDate().getYear();
            int startMonth = deposits.get(i).getStartDate().getMonthValue();
            int startDay = deposits.get(i).getStartDate().getDayOfMonth();
            DayOfWeek startWeek = deposits.get(i).getStartDate().getDayOfWeek();

            // 적금 만기 날짜

            int endYear = deposits.get(i).getEndDate().getYear();
            int endMonth = deposits.get(i).getEndDate().getMonthValue();
            int endDay = deposits.get(i).getEndDate().getDayOfMonth();
            DayOfWeek endWeek = deposits.get(i).getEndDate().getDayOfWeek();

            // 만기일의 요일을 구한 후 같을 때

            if(startWeek == endWeek){

                // 만기일 일 때 -> 이자 포함해서 사용자 자산 플러스 적금은 마이너스 해주고 해주고 삭제 / 만기일이 되면 기존 금액 * 이자율 추가 지급

                if(todayYear == endYear && todayMonth == endMonth && todayDay == endDay){

                    // 보유 금액 늘려주기 + 추가 이자 지급

                    user.getPoint().setHoldingPoint(user.getPoint().getHoldingPoint()+deposits.get(i).getInterestAmount() + deposits.get(i).getDepositAmount()* deposits.get(i).getDepositType().getDepositInterestRate()/100);

                    // 적금 금액 감소시키기

                    user.getPoint().setSavingPoint(user.getPoint().getSavingPoint()-deposits.get(i).getDepositAmount());
                    depositRepository.delete(deposits.get(i));
                }

                // 만기일은 아닐 때 : 시작 날짜랑 오늘이 같지 않다면-> 이자 올려주기

                else{
                    if(startYear == todayYear && startMonth==todayMonth && startDay==todayDay){}
                    else{
                        deposits.get(i).setInterestAmount(deposits.get(i).getInterestAmount() + deposits.get(i).getDepositAmount()*deposits.get(i).getDepositType().getDepositInterestRate()/100);
                    }
                }
            }
        }
    }

}
