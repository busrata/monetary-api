package com.maxijett.monetary.adapters.cashbox.rest.dto;

import com.maxijett.monetary.cashbox.usecase.CashBoxAdd;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashBoxAddDTO {
    private Long driverId;
    private ZonedDateTime createOn;
    private BigDecimal amount;
    private Long groupId;
    private Long clientId;
    private String payingAccount;
    private BigDecimal prePaidAmount;

    public CashBoxAdd toUseCase(){
        return CashBoxAdd.builder()
                .clientId(clientId)
                .groupId(groupId)
                .createOn(createOn)
                .amount(amount)
                .payingAccount(payingAccount)
                .driverId(driverId).build();
    }
}
