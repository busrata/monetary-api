package com.maxijett.monetary.cashbox.usecase;

import com.maxijett.monetary.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashBoxAdd implements UseCase {
    private Long driverId;
    private ZonedDateTime createOn;
    private BigDecimal amount;
    private Long groupId;
    private Long clientId;
    private String payingAccount;
}
