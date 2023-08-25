package com.moneyplay.MoneyPlay.service;

import com.moneyplay.MoneyPlay.domain.Corporation;
import com.moneyplay.MoneyPlay.domain.dto.CorporationAddDto;
import com.moneyplay.MoneyPlay.repository.CorporationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CorporationService {

    private final CorporationRepository corporationRepository;

    public String addCorporation(CorporationAddDto corporationAddDto) {
        corporationRepository.save(corporationAddDto.toEntity());

        return corporationAddDto.getCorporationName();
    }

    public Corporation getCorporation(String corporationName) {
        Corporation corporation = corporationRepository.findByCorporationName(corporationName).orElseThrow(
                () -> new NoSuchElementException("해당 회사가 존재하지 않습니다.")
        );

        return corporation;
    }
}
