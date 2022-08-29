package com.maxijett.monetary.adapters.cashbox.rest;

import com.maxijett.monetary.adapters.cashbox.rest.dto.CashBoxAddDTO;
import com.maxijett.monetary.adapters.cashbox.rest.dto.CashBoxDTO;
import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.usecase.CashBoxAdd;
import com.maxijett.monetary.cashbox.usecase.CashBoxAmountGet;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cashbox") // /driver-cash/fromCashBox
public class CashBoxController {

    private final UseCaseHandler<CashBox, CashBoxAdd> addCashToCashBoxUseCaseHandler;

    private final UseCaseHandler<CashBox, CashBoxAmountGet> getAmountFromCashBoxByClientIdOrGroupIdUseCaseHandler;

    @PostMapping
    public ResponseEntity<CashBoxDTO> cashBoxAdd(@RequestBody CashBoxAddDTO cashBoxDTO) {
        log.info("Rest request to cashBoxAdd: {}", cashBoxDTO);
        CashBox cashBox = addCashToCashBoxUseCaseHandler.handle(cashBoxDTO.toUseCase());
        return new ResponseEntity<>(CashBoxDTO.fromModel(cashBox), HttpStatus.OK);
    }

    @GetMapping(value = "/amount-by-owner")
    public ResponseEntity<CashBoxDTO> cashBoxGetAmount(@RequestParam(required = false) Long clientId, @RequestParam(required = false) Long groupId) {
        log.info("Rest request to cashBoxGetAmount with clientId: {} or groupId: {}", clientId, groupId);
        CashBox cashBox = getAmountFromCashBoxByClientIdOrGroupIdUseCaseHandler.handle(CashBoxAmountGet.fromModel(clientId, groupId));
        return new ResponseEntity<>(CashBoxDTO.fromModel(cashBox), HttpStatus.OK);
    }
}
