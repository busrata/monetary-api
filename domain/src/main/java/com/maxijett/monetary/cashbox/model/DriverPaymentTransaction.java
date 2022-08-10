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
public class DriverPaymentTransaction {

    private Long id;

    private String orderNumber;

    private ZonedDateTime dateTime;

    private Long driverId;

    private BigDecimal paymentCash;

    private Long userId;

    private Long parentTransactionId;
}
