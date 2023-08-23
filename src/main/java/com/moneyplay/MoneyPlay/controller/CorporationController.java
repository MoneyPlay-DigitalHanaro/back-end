package com.moneyplay.MoneyPlay.controller;

import com.moneyplay.MoneyPlay.domain.dto.CorporationAddDto;
import com.moneyplay.MoneyPlay.service.CorporationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/corporation")
public class CorporationController {

    private final CorporationService corporationService;

    @ApiOperation(value = "주식 회사 추가")
    @PostMapping("/add")
    public ResponseEntity<?> corporationAdd(HttpServletRequest request, @Validated @RequestBody CorporationAddDto corporationAddDto) {
        try {
            String corporationName = corporationService.addCorporation(corporationAddDto);

            return new ResponseEntity<>(corporationName + "등록완료", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
