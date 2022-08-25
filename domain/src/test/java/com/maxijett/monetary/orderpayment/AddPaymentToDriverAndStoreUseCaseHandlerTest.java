package com.maxijett.monetary.orderpayment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.maxijett.monetary.cashbox.adapters.DriverCashFakeDataAdapter;
import com.maxijett.monetary.cashbox.adapters.DriverPaymentTransactionFakeDataAdapter;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import com.maxijett.monetary.orderpayment.useCase.OrderPayment;
import com.maxijett.monetary.store.adapters.StoreCollectionFakeDataAdapter;
import com.maxijett.monetary.store.adapters.StorePaymentTransactionFakeDataAdapter;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import com.maxijett.monetary.store.model.enumeration.TariffType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddPaymentToDriverAndStoreUseCaseHandlerTest {


  DriverCashFakeDataAdapter driverCashPort;

  DriverPaymentTransactionFakeDataAdapter driverPaymentTransactionPort;

  StoreCollectionFakeDataAdapter storeCollectionPort;

  StorePaymentTransactionFakeDataAdapter storePaymentTransactionPort;

  AddPaymentToDriverAndStoreUseCaseHandler handler;


  @BeforeEach
  public void setUp(){
    driverCashPort = new DriverCashFakeDataAdapter();
    driverPaymentTransactionPort = new DriverPaymentTransactionFakeDataAdapter();
    storeCollectionPort = new StoreCollectionFakeDataAdapter();
    storePaymentTransactionPort = new StorePaymentTransactionFakeDataAdapter();
    handler = new AddPaymentToDriverAndStoreUseCaseHandler(driverPaymentTransactionPort,driverCashPort,storePaymentTransactionPort,storeCollectionPort);
  }

  @Test
  public void shouldBeAddDriverAndStoreWithCashPayment(){
    //Given
    OrderPayment orderPayment = OrderPayment.builder()
        .orderNumber("123456789")
        .storeId(3L)
        .driverId(2L)
        .groupId(1L)
        .cash(BigDecimal.valueOf(20))
        .pos(BigDecimal.ZERO)
        .clientId(20L)
        .build();

    //When
    Boolean response = handler.handle(orderPayment);

    //Then
    Assertions.assertTrue(response);

    driverCashPort.assertContains(DriverCash.builder()
            .id(1L)
        .driverId(2L)
        .cash(BigDecimal.valueOf(140))
        .groupId(1L)
        .clientId(20L)
        .prepaidCollectionCash(BigDecimal.valueOf(75))
        .build()
    );

    var driverTransactionResponse = driverPaymentTransactionPort.getDriverPaymentTransactions().get(0);

    assertEquals(DriverEventType.PACKAGE_DELIVERED, driverTransactionResponse.getEventType());
    assertEquals(orderPayment.getCash(), driverTransactionResponse.getPaymentCash());
    assertEquals(orderPayment.getDriverId(), driverTransactionResponse.getDriverId());
    assertEquals(orderPayment.getPos(), BigDecimal.ZERO);
    assertEquals(orderPayment.getOrderNumber(), driverTransactionResponse.getOrderNumber());


    storeCollectionPort.assertContains(StoreCollection.builder()
        .storeId(3L)
        .cash(BigDecimal.valueOf(75))
        .pos(BigDecimal.valueOf(100))
        .groupId(1L)
        .clientId(20L)
        .tariffType(TariffType.TAXIMETER_HOT)
        .build()
    );

    var storeTransactionResponse = storePaymentTransactionPort.transactionList.get(0);

    assertEquals(StoreEventType.PACKAGE_DELIVERED, storeTransactionResponse.getEventType());
    assertEquals(orderPayment.getCash(), storeTransactionResponse.getCash());
    assertEquals(orderPayment.getPos(), storeTransactionResponse.getPos());
    assertEquals(orderPayment.getOrderNumber(), storeTransactionResponse.getOrderNumber());
    assertEquals(orderPayment.getStoreId(), storeTransactionResponse.getStoreId());

  }

  @Test
  public void shouldBeAddDriverAndStoreWithPosPayment(){
    //Given
    OrderPayment orderPayment = OrderPayment.builder()
        .orderNumber("1234567890")
        .storeId(3L)
        .driverId(2L)
        .groupId(1L)
        .cash(BigDecimal.ZERO)
        .pos(BigDecimal.valueOf(40))
        .clientId(20L)
        .build();

    //When
    Boolean response = handler.handle(orderPayment);

    //Then
    Assertions.assertTrue(response);

    storeCollectionPort.assertContains(StoreCollection.builder()
        .pos(BigDecimal.valueOf(140))
        .storeId(3L)
        .groupId(1L)
        .cash(BigDecimal.valueOf(55))
        .clientId(20L)
        .tariffType(TariffType.TAXIMETER_HOT)
        .build()
    );

    var storeTransactionResponse = storePaymentTransactionPort.transactionList.get(0);

    assertEquals(orderPayment.getOrderNumber(), storeTransactionResponse.getOrderNumber());
    assertEquals(StoreEventType.PACKAGE_DELIVERED, storeTransactionResponse.getEventType());
    assertEquals(BigDecimal.ZERO, storeTransactionResponse.getCash());
    assertEquals(orderPayment.getPos(), storeTransactionResponse.getPos());
    assertEquals(orderPayment.getStoreId(), storeTransactionResponse.getStoreId());

  }

  @Test
  public void shouldBeAddDriverAndStoreWithMixPayment(){
    //Given
    OrderPayment orderPayment = OrderPayment.builder()
        .orderNumber("1234567899")
        .storeId(3L)
        .driverId(2L)
        .groupId(1L)
        .cash(BigDecimal.valueOf(25))
        .pos(BigDecimal.valueOf(35))
        .clientId(20L)
        .build();

    //When
    Boolean response = handler.handle(orderPayment);

    //Then
    Assertions.assertTrue(response);

    driverCashPort.assertContains(DriverCash.builder()
        .id(1L)
        .cash(BigDecimal.valueOf(145))
        .groupId(1L)
        .driverId(2L)
        .prepaidCollectionCash(BigDecimal.valueOf(75))
        .clientId(20L)
        .build()
    );

    var driverTransactionResponse = driverPaymentTransactionPort.getDriverPaymentTransactions().get(0);

    assertEquals(orderPayment.getOrderNumber(), driverTransactionResponse.getOrderNumber());
    assertEquals(orderPayment.getCash(), driverTransactionResponse.getPaymentCash());
    assertEquals(DriverEventType.PACKAGE_DELIVERED, driverTransactionResponse.getEventType());
    assertEquals(orderPayment.getDriverId(), driverTransactionResponse.getDriverId());
    assertEquals(orderPayment.getGroupId(), driverTransactionResponse.getGroupId());

    storeCollectionPort.assertContains(StoreCollection.builder()
        .storeId(3L)
        .pos(BigDecimal.valueOf(135))
        .cash(BigDecimal.valueOf(80))
        .groupId(1L)
        .clientId(20L)
        .tariffType(TariffType.TAXIMETER_HOT)
        .build()
    );

    var storePaymentTransaction = storePaymentTransactionPort.transactionList.get(0);

    assertEquals(orderPayment.getOrderNumber(), storePaymentTransaction.getOrderNumber());
    assertEquals(orderPayment.getCash(), storePaymentTransaction.getCash());
    assertEquals(orderPayment.getPos(), storePaymentTransaction.getPos());
    assertEquals(orderPayment.getStoreId(), storePaymentTransaction.getStoreId());
    assertEquals(StoreEventType.PACKAGE_DELIVERED, storePaymentTransaction.getEventType());

  }

}
