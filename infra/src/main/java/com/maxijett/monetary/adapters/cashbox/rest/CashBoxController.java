package com.maxijett.monetary.adapters.cashbox.rest;

import com.maxijett.monetary.adapters.cashbox.rest.dto.CashBoxAddDTO;
import com.maxijett.monetary.adapters.cashbox.rest.dto.CashBoxDTO;
import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.usecase.CashBoxAdd;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cashbox") // /driver-cash/fromCashBox
public class CashBoxController {

    private final UseCaseHandler<CashBox, CashBoxAdd> addCashToCashBoxUseCaseHandler;

    @PostMapping
    public ResponseEntity<CashBoxDTO> cashBoxAdd(@RequestBody CashBoxAddDTO cashBoxDTO) {
        log.info("Rest request to cashBoxAdd: {}", cashBoxDTO);
        CashBox cashBox = addCashToCashBoxUseCaseHandler.handle(cashBoxDTO.toUseCase());
        return new ResponseEntity<>(CashBoxDTO.fromModel(cashBox), HttpStatus.OK);
    }
}
