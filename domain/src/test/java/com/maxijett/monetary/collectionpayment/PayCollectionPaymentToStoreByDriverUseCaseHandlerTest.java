package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.driver.adapters.DriverCashFakeDataAdapter;
import com.maxijett.monetary.collectionpayment.adapters.CollectionPaymentFakeDataAdapter;
import com.maxijett.monetary.store.adapters.StoreCollectionFakeDataAdapter;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.enumeration.TariffType;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PayCollectionPaymentToStoreByDriverUseCaseHandlerTest {

  DriverCashFakeDataAdapter driverCashFakeDataAdapter;
  StoreCollectionFakeDataAdapter storeCollectionFakeDataAdapter;

  CollectionPaymentPort collectionPaymentPort = new CollectionPaymentFakeDataAdapter();
  PayCollectionPaymentToStoreByDriverUseCaseHandler handler;


  @BeforeEach
  public void setUp(){
    driverCashFakeDataAdapter = new DriverCashFakeDataAdapter();
    storeCollectionFakeDataAdapter = new StoreCollectionFakeDataAdapter();
    handler = new PayCollectionPaymentToStoreByDriverUseCaseHandler(collectionPaymentPort, driverCashFakeDataAdapter, storeCollectionFakeDataAdapter);
  }

  @Test
  public void shouldBeSaveCollectionPayment(){

    //Given
    CollectionPaymentCreate collectionPaymentUseCase = CollectionPaymentCreate.builder()
        .driverId(11L)
        .storeId(12L)
        .cash(BigDecimal.TEN)
        .pos(BigDecimal.ZERO)
        .date(ZonedDateTime.now(ZoneId.of("UTC")))
        .clientId(20L)
        .groupId(21L).build();

    //When
    CollectionPayment response = handler.handle(collectionPaymentUseCase);

    //Then
    Assertions.assertEquals(collectionPaymentUseCase.getCash(), response.getCash());
    Assertions.assertEquals(collectionPaymentUseCase.getDriverId(),response.getDriverId());
    Assertions.assertEquals(collectionPaymentUseCase.getStoreId(), response.getStoreId());
    Assertions.assertEquals(collectionPaymentUseCase.getGroupId(), response.getGroupId());
    Assertions.assertEquals(collectionPaymentUseCase.getClientId(), response.getClientId());

  }

  @Test
  public void shouldBeUpdateDriverAndStoreCashes(){

    //Given
    CollectionPaymentCreate collectionPaymentUseCase = CollectionPaymentCreate.builder()
        .driverId(2L)
        .storeId(12L)
        .cash(BigDecimal.TEN)
        .pos(BigDecimal.ZERO)
        .date(ZonedDateTime.now(ZoneId.of("UTC")))
        .clientId(20L)
        .groupId(1L).build();

    //When
    CollectionPayment response = handler.handle(collectionPaymentUseCase);

    //Then
    Assertions.assertNotNull(response.getId());
    Assertions.assertEquals(collectionPaymentUseCase.getDriverId(), response.getDriverId());
    Assertions.assertEquals(collectionPaymentUseCase.getStoreId(), response.getStoreId());
    Assertions.assertEquals(collectionPaymentUseCase.getCash(), response.getCash());
    Assertions.assertEquals(collectionPaymentUseCase.getGroupId(), response.getGroupId());
      Assertions.assertNotNull(response.getCreateOn());

    driverCashFakeDataAdapter.assertContains(
        DriverCash.builder()
            .id(1L)
            .driverId(2L)
            .cash(BigDecimal.valueOf(110L))
            .clientId(20L)
            .groupId(1L)
            .prepaidCollectionCash(BigDecimal.valueOf(75L))
            .build()
    );

    storeCollectionFakeDataAdapter.assertContains(
        StoreCollection.builder()
            .storeId(12L)
            .cash(new BigDecimal(45))
            .pos(new BigDecimal(100))
            .clientId(20L)
            .groupId(1L)
            .tariffType(TariffType.TAXIMETER_HOT)
            .build());

  }


}
