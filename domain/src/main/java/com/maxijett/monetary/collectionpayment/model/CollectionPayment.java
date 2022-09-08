package com.maxijett.monetary.collectionpayment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CollectionPayment {

    private Long id;
    private Long storeId;
    private Long driverId;
    private BigDecimal cash;
    private BigDecimal pos;
    private ZonedDateTime createOn;
    private Long groupId;
    private Long clientId;
    private Boolean isDeleted;

}
