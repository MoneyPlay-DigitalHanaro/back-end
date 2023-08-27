package com.moneyplay.MoneyPlay.service;

import com.moneyplay.MoneyPlay.domain.CurrentStock;
import com.moneyplay.MoneyPlay.domain.User;
import com.moneyplay.MoneyPlay.domain.dto.MyDepositDto;
import com.moneyplay.MoneyPlay.domain.dto.MyPointDto;
import com.moneyplay.MoneyPlay.domain.dto.MyStockDto;
import com.moneyplay.MoneyPlay.domain.dto.StockDataDto;
import com.moneyplay.MoneyPlay.repository.CurrentStockRepository;
import com.moneyplay.MoneyPlay.repository.PointRepository;
import com.moneyplay.MoneyPlay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final UserRepository userRepository;

    private final PointRepository pointRepository;

    private final CurrentStockRepository currentStockRepository;

    public MyPointDto findUserPoint(Long userId, List<MyStockDto> myStockDtoList, MyDepositDto myDepositDto) {
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 유저 정보 입니다.")
        );

        // 유저의 포인트 정보
        Long totalPoint;     // 총 포인트 가치
        Long changePointValue = 0L;       // 수익 금액
        double changePointRate;     // 수익률
        Long availablePoint;     // 사용가능한 포인트
        Long totalStockPoint = 0L;       // 총 주식 포인트
        Long totalDepositPoint = 0L;  //총 적금 포인트

        for (int i = 0; i<myStockDtoList.size(); i++) {
            changePointValue += myStockDtoList.get(i).getChangeStockValue();
            totalStockPoint += myStockDtoList.get(i).getTotalStockValue();
        }

        if (myDepositDto != null)
            myDepositDto.getDepositAmount();

        totalPoint = totalDepositPoint + totalStockPoint;
        changePointValue += myDepositDto.getInterestAmount();
        changePointRate = ((double)changePointValue/totalPoint)*100;
        availablePoint = user.getPoint().getHoldingPoint();
        totalDepositPoint = myDepositDto.getDepositAmount() + myDepositDto.getInterestAmount();

        MyPointDto myPointDto = new MyPointDto(totalPoint, changePointValue, changePointRate,availablePoint,totalStockPoint, totalDepositPoint);

        return myPointDto;
    }

    public List<MyStockDto> findUserStock(Long userId, List<CurrentStock> currentStockList, List<StockDataDto> userStockDataList) {
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 유저 정보입니다.")
        );

        // MyStockDto
        String name;                // 주식 이름
        int presentPrice;           // 현재 가격
        int hodingStockCount;       // 가지고 있는 주식 수
        Long totalStockValue;       // 가지고 있는 주식의 총 가치
        Long changeStockValue;      // 가지고 있는 주식의 총 수익금액
        double changeStockRate;     // 가지고 있는 주식의 총 수익률
        int totalBuyStockPoint;     // 해당 주식을 구매하는데 사용한 총 포인트

        List<MyStockDto> myStockDtoList = new ArrayList<>();

        for (int i=0; i<userStockDataList.size(); i++) {
            name = userStockDataList.get(i).getName();
            presentPrice = Integer.parseInt(userStockDataList.get(i).getStockPresentPrice());
            hodingStockCount = currentStockList.get(i).getStockHoldingCount();
            totalStockValue = presentPrice*hodingStockCount*1L;
            totalBuyStockPoint = currentStockList.get(i).getTotalPrice();
            changeStockValue = totalStockValue - totalBuyStockPoint;
            changeStockRate = ((double)changeStockValue/totalBuyStockPoint)*100;
            MyStockDto myStockDto = new MyStockDto(name, presentPrice, hodingStockCount, totalStockValue, changeStockValue, changeStockRate);

            myStockDtoList.add(myStockDto);
        }

        return myStockDtoList;
    }

    public List<CurrentStock> findUserCurrentStock(Long userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 유저 정보입니다.")
        );
        List<CurrentStock> currentStockList = currentStockRepository.findByUser(user).orElseThrow(
                null
        );

        return currentStockList;
    }

    public Long getTotalStockValue(List<MyStockDto> myStockDtoList) {
        Long totalStockValue = 0L;
        for (int i = 0; i<myStockDtoList.size(); i++) {
            totalStockValue += myStockDtoList.get(i).getTotalStockValue();
        }
        return totalStockValue;
    }
}
