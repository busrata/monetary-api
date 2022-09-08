package com.maxijett.monetary.collectionreport.model;

import com.maxijett.monetary.collectionreport.model.enumerations.WarmthType;
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
public class CollectionReport {

    private Long id;

    private String orderNumber;

    private Long storeId;

    private ZonedDateTime paymentDate;

    private BigDecimal cash;

    private BigDecimal pos;

    private BigDecimal cashCommission;

    private BigDecimal posCommission;

    private BigDecimal distanceFee;

    private int deliveryDistance;

    private Long driverId;

    private Long groupId;

    private WarmthType warmthType;

}
