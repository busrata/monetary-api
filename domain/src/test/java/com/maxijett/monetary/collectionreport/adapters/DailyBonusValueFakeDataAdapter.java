package com.maxijett.monetary.collectionreport.adapters;

import com.maxijett.monetary.collectionreport.model.DailyBonusValue;
import com.maxijett.monetary.collectionreport.port.DailyBonusValuePort;

public class DailyBonusValueFakeDataAdapter implements DailyBonusValuePort {


    @Override
    public DailyBonusValue getDailyBonusValue() {
        return DailyBonusValue.builder()
                .nightShiftSize(10)
                .nightShiftRate(1.5)
                .coldDivide(100)
                .coldOpening(5)
                .coldRate(0.032)
                .hotRate(0.09)
                .nightShiftMinValue(15)
                .build();
    }
}
