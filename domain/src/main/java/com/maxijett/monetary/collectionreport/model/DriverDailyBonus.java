package com.maxijett.monetary.collectionreport.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDailyBonus {

    private BigDecimal NoonShiftDailyBonus;

    private BigDecimal NightShiftDailyBonus;

}
