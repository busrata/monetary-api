package com.maxijett.monetary.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.maxijett.monetary.driver.adapters.DriverCashFakeDataAdapter;
import com.maxijett.monetary.driver.useCase.DriverCashListRetrieve;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetDriverCashInstantListGreaterThanZeroUseCaseHandlerTest {

  GetDriverCashInstantListGreaterThanZeroUseCaseHandler handler;

  @BeforeEach
  public void setUp(){
    handler = new GetDriverCashInstantListGreaterThanZeroUseCaseHandler(new DriverCashFakeDataAdapter());
  }

  @Test
  public void shouldGetListGreaterThanZeroByGroupId(){

    DriverCashListRetrieve driverCashListRetrieve = DriverCashListRetrieve.builder().groupId(20L).build();

    var responseList = handler.handle(driverCashListRetrieve);

    Assertions.assertNotNull(responseList);
    Assertions.assertEquals(3, responseList.size());

  }

  @Test
  public void shouldGetListGreaterThanZeroByClientId(){

    DriverCashListRetrieve driverCashListRetrieve = DriverCashListRetrieve.builder().clientId(20000L).build();

    var responseList = handler.handle(driverCashListRetrieve);

    Assertions.assertNotNull(responseList);
    Assertions.assertEquals(4, responseList.size());

    assertThat(responseList).isNotNull().hasSize(4)
        .extracting("driverId", "clientId", "cash")
        .containsExactlyInAnyOrder(
            tuple(1L, 20000L, BigDecimal.valueOf(12)),
            tuple(2L, 20000L, BigDecimal.valueOf(18)),
            tuple(3L, 20000L, BigDecimal.valueOf(15.06)),
            tuple(4L, 20000L, BigDecimal.valueOf(41))
        );

  }

}
