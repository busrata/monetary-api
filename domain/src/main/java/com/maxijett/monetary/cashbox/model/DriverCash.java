package com.maxijett.monetary.cashbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverCash {
    private Long id;

    private BigDecimal amount;

    private Long driverId;

    private Instant createOn;

    private Long storeId;

    private Long clientId;

    private String payingAccount;

    private Long groupId;

    private BigDecimal prepaidAmount;
}
