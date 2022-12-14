package com.maxijett.monetary.driver;

import com.maxijett.monetary.driver.adapters.DriverCashFakeDataAdapter;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.useCase.DriverCashRetrieve;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetDriverAmountFromDriverCashUseCaseHandlerTest {

    DriverCashFakeDataAdapter driverCashPort = new DriverCashFakeDataAdapter();

    GetDriverAmountFromDriverCashUseCaseHandler handler = new GetDriverAmountFromDriverCashUseCaseHandler(
            driverCashPort);

    @Test
    public void shouldBeGetDriverAmountByDriverIdAndGroupId() {
        //Given
        DriverCashRetrieve driverCashUseCase = DriverCashRetrieve.builder()
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
