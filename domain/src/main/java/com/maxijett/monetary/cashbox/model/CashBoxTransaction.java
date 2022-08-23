package com.maxijett.monetary.cashbox.model;

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
public class CashBoxTransaction {

    private Long id;

    private ZonedDateTime dateTime;

    private Long driverId;

    private BigDecimal amount;

    private String payingAccount;

}
