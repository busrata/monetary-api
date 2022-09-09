package com.maxijett.monetary.collectionreport.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommissionConstantPos {

    private Long id;

    private BigDecimal rate;

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

    private Long clientId;

}
