package com.maxijett.monetary.collectionreport;

import com.maxijett.monetary.collectionreport.adapters.CollectionReportFakeDataAdapter;
import com.maxijett.monetary.collectionreport.adapters.DailyBonusValueFakeDataAdapter;
import com.maxijett.monetary.collectionreport.adapters.ShiftTimeFakeDataAdapter;
import com.maxijett.monetary.collectionreport.model.DriverDailyBonus;
import com.maxijett.monetary.driver.useCase.DriverGetDailyBonus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DailyBonusGetUseCaseHandlerTest {

    DailyBonusGetUseCaseHandler handler;

    CollectionReportFakeDataAdapter collectionReportPort;

    ShiftTimeFakeDataAdapter shiftTimePort;

    DailyBonusValueFakeDataAdapter dailyBonusValuePort;


    @BeforeEach
    public void setUp() {
        collectionReportPort = new CollectionReportFakeDataAdapter();

        shiftTimePort = new ShiftTimeFakeDataAdapter();

        dailyBonusValuePort = new DailyBonusValueFakeDataAdapter();

        handler = new DailyBonusGetUseCaseHandler(collectionReportPort, shiftTimePort, dailyBonusValuePort);
    }

    @Test
    public void calculateDailyBonus() {

        //Given
        DriverGetDailyBonus driverGetDailyBonus = DriverGetDailyBonus.builder()
                .driverId(1L)
                .isRequestForMobil(false)
                .startDate(LocalDate.of(2022, 8, 01))
                .endDate(null)
                .build();

        //When
        DriverDailyBonus response = handler.handle(driverGetDailyBonus);

        //Then
        assertEquals(BigDecimal.valueOf(15), response.getNightShiftDailyBonus());
        assertEquals(BigDecimal.valueOf(13.75), response.getNoonShiftDailyBonus().setScale(2, RoundingMode.HALF_EVEN));


    }

    @Test
    public void calculateDailyBonusForMobil() {

        //Given
        DriverGetDailyBonus driverGetDailyBonus = DriverGetDailyBonus.builder()
                .driverId(1L)
                .isRequestForMobil(true)
                .startDate(LocalDate.of(2022, 8, 02))
                .endDate(null)
                .build();

        //When
        DriverDailyBonus response = handler.handle(driverGetDailyBonus);

        //Then
        assertEquals(BigDecimal.valueOf(15), response.getNightShiftDailyBonus());
        assertEquals(BigDecimal.valueOf(13.75), response.getNoonShiftDailyBonus().setScale(2, RoundingMode.HALF_EVEN));

    }


}
