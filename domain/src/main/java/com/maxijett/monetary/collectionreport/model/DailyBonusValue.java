package com.maxijett.monetary.collectionreport.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyBonusValue {

    private double hotRate;

    private double coldRate;

    private int coldOpening;

    private int coldDivide;

    private double nightShiftRate;

    private int nightShiftMinValue;

    private int nightShiftSize;


}
