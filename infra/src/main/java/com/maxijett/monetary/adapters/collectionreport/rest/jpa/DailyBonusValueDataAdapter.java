package com.maxijett.monetary.adapters.collectionreport.rest.jpa;

import com.maxijett.monetary.collectionreport.model.DailyBonusValue;
import com.maxijett.monetary.collectionreport.port.DailyBonusValuePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DailyBonusValueDataAdapter implements DailyBonusValuePort {

    @Value("${dailyBonus.hot.rate}")
    private double hotRate;

    @Value("${dailyBonus.cold.rate}")
    private double coldRate;

    @Value("${dailyBonus.cold.opening}")
    private int coldOpening;

    @Value("${dailyBonus.cold.divide}")
    private int coldDivide;

    @Value("${dailyBonus.nightShift.rate}")
    private double nightShiftRate;

    @Value("${dailyBonus.nightShift.minValue}")
    private int nightShiftMinValue;

    @Value("${dailyBonus.nightShift.size}")
    private int nightShiftSize;

    @Override
    public DailyBonusValue getDailyBonusValue() {
        return DailyBonusValue.builder()
                .coldDivide(coldDivide)
                .coldOpening(coldOpening)
                .coldRate(coldRate)
                .hotRate(hotRate)
                .nightShiftMinValue(nightShiftMinValue)
                .nightShiftRate(nightShiftRate)
                .nightShiftSize(nightShiftSize)
                .build();
    }
}
