package com.maxijett.monetary.driver;

import com.maxijett.monetary.cashbox.adapters.DriverCashFakeDataAdapter;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.usecase.DriverCashGet;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetDriverAmountFromDriverCashUseCaseHandlerTest {

  DriverCashFakeDataAdapter driverCashPort = new DriverCashFakeDataAdapter();

  GetDriverAmountFromDriverCashUseCaseHandler handler = new GetDriverAmountFromDriverCashUseCaseHandler(
      driverCashPort);

  @Test
  public void shouldBeGetDriverAmountByDriverIdAndGroupId() {
    //Given
    DriverCashGet driverCashUseCase = DriverCashGet.builder()
        .driverId(2L)
        .groupId(1L)
        .build();

    //When
    DriverCash response = handler.handle(driverCashUseCase);

    //Then
    assertNotNull(response.getId());
    assertEquals(driverCashUseCase.getDriverId(), response.getDriverId());
    assertEquals(driverCashUseCase.getGroupId(), response.getGroupId());
    assertEquals(BigDecimal.valueOf(120), response.getCash());
    assertEquals(BigDecimal.valueOf(75), response.getPrepaidCollectionCash());
    assertEquals(20L, response.getClientId());

  }
}