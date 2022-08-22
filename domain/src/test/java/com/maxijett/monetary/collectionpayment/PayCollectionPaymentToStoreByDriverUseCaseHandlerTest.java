package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.cashbox.adapters.DriverCashFakeDataAdapter;
import com.maxijett.monetary.cashbox.adapters.DriverPaymentTransactionFakeDataAdapter;
import com.maxijett.monetary.collectionpayment.adapters.CollectionPaymentFakeDataAdapter;
import com.maxijett.monetary.collectionpayment.adapters.StoreCollectionFakeDataAdapter;
import com.maxijett.monetary.store.adapters.StorePaymentTransactionFakeDataAdapter;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import com.maxijett.monetary.store.model.enumeration.TariffType;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PayCollectionPaymentToStoreByDriverUseCaseHandlerTest {

  DriverCashFakeDataAdapter driverCashFakeDataAdapter;
  DriverPaymentTransactionFakeDataAdapter driverPaymentTransactionFakeDataAdapter;

  StoreCollectionFakeDataAdapter storeCollectionFakeDataAdapter;

  StorePaymentTransactionFakeDataAdapter storePaymentTransactionFakeDataAdapter;

  CollectionPaymentPort collectionPaymentPort = new CollectionPaymentFakeDataAdapter();
  PayCollectionPaymentToStoreByDriverUseCaseHandler handler;


  @BeforeEach
  public void setUp(){
    driverCashFakeDataAdapter = new DriverCashFakeDataAdapter();
    driverPaymentTransactionFakeDataAdapter = new DriverPaymentTransactionFakeDataAdapter();
    storeCollectionFakeDataAdapter = new StoreCollectionFakeDataAdapter();
    storePaymentTransactionFakeDataAdapter = new StorePaymentTransactionFakeDataAdapter();
    handler = new PayCollectionPaymentToStoreByDriverUseCaseHandler(collectionPaymentPort, driverCashFakeDataAdapter, storeCollectionFakeDataAdapter,driverPaymentTransactionFakeDataAdapter,storePaymentTransactionFakeDataAdapter);
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
    Assertions.assertNotNull(response.getDate());

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
            .tariffType(TariffType.TAXIMETER_HOT)
            .build());

  }

  @Test
  public void shouldBeSaveDriverAndStoreTransactions(){

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

    var driverPaymentTransaction = driverPaymentTransactionFakeDataAdapter.getDriverPaymentTransactions().get(0);

    Assertions.assertEquals(collectionPaymentUseCase.getCash(), driverPaymentTransaction.getPaymentCash());
    Assertions.assertEquals(collectionPaymentUseCase.getDriverId(), driverPaymentTransaction.getDriverId());
    Assertions.assertEquals(DriverEventType.DRIVER_PAY, driverPaymentTransaction.getEventType());
    Assertions.assertEquals(collectionPaymentUseCase.getGroupId(), driverPaymentTransaction.getGroupId());


    var storePaymentTransaction = storePaymentTransactionFakeDataAdapter.transactionList.get(0);

    Assertions.assertEquals(collectionPaymentUseCase.getCash(), storePaymentTransaction.getCash());
    Assertions.assertEquals(collectionPaymentUseCase.getStoreId(), storePaymentTransaction.getStoreId());
    Assertions.assertEquals(collectionPaymentUseCase.getClientId(), storePaymentTransaction.getClientId());
    Assertions.assertEquals(StoreEventType.DRIVER_PAY, storePaymentTransaction.getEventType());

  }

}
