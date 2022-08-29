package com.maxijett.monetary.driver.model;

import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverPaymentTransaction implements Serializable {

    private Long id;

    private String orderNumber;

    private ZonedDateTime dateTime;

    private Long driverId;

    private BigDecimal paymentCash;

    private Long userId;

    private Long parentTransactionId;

    private DriverEventType eventType;

    private Long groupId;
}
