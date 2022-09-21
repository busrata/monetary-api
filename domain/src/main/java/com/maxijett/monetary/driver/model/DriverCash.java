package com.maxijett.monetary.driver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverCash {

    private Long id;
    private Long driverId;
    private Long groupId;
    private BigDecimal cash;
    private Long clientId;
    private BigDecimal prepaidCollectionCash;

}
