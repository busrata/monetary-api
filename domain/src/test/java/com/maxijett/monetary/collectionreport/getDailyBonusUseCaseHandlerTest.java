package com.maxijett.monetary.collectionreport;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.maxijett.monetary.collectionreport.adapters.CollectionReportFakeDataAdapter;
import com.maxijett.monetary.collectionreport.adapters.DailyBonusValueFakeDataAdapter;
import com.maxijett.monetary.collectionreport.adapters.ShiftTimeFakeDataAdapter;
import com.maxijett.monetary.collectionreport.model.DriverDailyBonus;
import com.maxijett.monetary.collectionreport.port.ShiftTimePort;
import com.maxijett.monetary.driver.useCase.DriverGetDailyBonus;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class getDailyBonusUseCaseHandlerTest {

  GetDailyBonusUseCaseHandler handler;

  CollectionReportFakeDataAdapter collectionReportPort;

  ShiftTimeFakeDataAdapter shiftTimePort;

  DailyBonusValueFakeDataAdapter dailyBonusValuePort;


  @BeforeEach
  public void setUp(){
    collectionReportPort = new CollectionReportFakeDataAdapter();

    shiftTimePort = new ShiftTimeFakeDataAdapter();

    dailyBonusValuePort = new DailyBonusValueFakeDataAdapter();

    handler = new GetDailyBonusUseCaseHandler(collectionReportPort,shiftTimePort,dailyBonusValuePort);
  }

  @Test
  public void calculateDailyBonus(){

    //Given
    DriverGetDailyBonus driverGetDailyBonus = DriverGetDailyBonus.builder()
        .driverId(1L)
        .isRequestForMobil(false)
        .startDate(LocalDate.of(  2022,8,01))
        .endDate(null)
        .build();

    //When
    DriverDailyBonus response = handler.handle(driverGetDailyBonus);

    //Then
    assertEquals(BigDecimal.valueOf(15), response.getNightShiftDailyBonus());
    assertEquals(BigDecimal.valueOf(13.75), response.getNoonShiftDailyBonus().setScale(2, RoundingMode.HALF_EVEN));


  }


}
