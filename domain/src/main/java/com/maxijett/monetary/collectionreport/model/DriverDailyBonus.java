package com.maxijett.monetary.collectionreport.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDailyBonus {

    private BigDecimal NoonShiftDailyBonus;

    private BigDecimal NightShiftDailyBonus;

}
