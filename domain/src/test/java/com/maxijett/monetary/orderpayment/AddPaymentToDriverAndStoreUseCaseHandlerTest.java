package com.maxijett.monetary.orderpayment;

import com.maxijett.monetary.driver.adapters.DriverCashFakeDataAdapter;
import com.maxijett.monetary.driver.adapters.DriverPaymentTransactionFakeDataAdapter;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import com.maxijett.monetary.orderpayment.useCase.OrderPayment;
import com.maxijett.monetary.store.adapters.StoreCollectionFakeDataAdapter;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.enumeration.TariffType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddPaymentToDriverAndStoreUseCaseHandlerTest {


    DriverCashFakeDataAdapter driverCashPort;

    StoreCollectionFakeDataAdapter storeCollectionPort;

    DriverPaymentTransactionFakeDataAdapter driverTransactionPort;

    AddPaymentToDriverAndStoreUseCaseHandler handler;


    @BeforeEach
    public void setUp() {
        driverCashPort = new DriverCashFakeDataAdapter();
        storeCollectionPort = new StoreCollectionFakeDataAdapter();
        driverTransactionPort = new DriverPaymentTransactionFakeDataAdapter();
        handler = new AddPaymentToDriverAndStoreUseCaseHandler(driverCashPort, storeCollectionPort, driverTransactionPort);
    }

    @Test
    public void shouldBeAddDriverAndStoreWithCashPayment() {
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
        handler.handle(orderPayment);

        //Then
        driverCashPort.assertContains(DriverCash.builder()
                .id(1L)
                .driverId(2L)
                .cash(BigDecimal.valueOf(140))
                .groupId(1L)
                .clientId(20L)
                .prepaidCollectionCash(BigDecimal.valueOf(75))
                .build()
        );


        storeCollectionPort.assertContains(StoreCollection.builder()
                .storeId(3L)
                .cash(BigDecimal.valueOf(75))
                .pos(BigDecimal.valueOf(100))
                .groupId(1L)
                .clientId(20L)
                .tariffType(TariffType.TAXIMETER_HOT)
                .build()
        );

    }

    @Test
    public void shouldBeAddDriverAndStoreWithPosPayment() {
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
        handler.handle(orderPayment);

        //Then
        storeCollectionPort.assertContains(StoreCollection.builder()
                .pos(BigDecimal.valueOf(140))
                .storeId(3L)
                .groupId(1L)
                .cash(BigDecimal.valueOf(55))
                .clientId(20L)
                .tariffType(TariffType.TAXIMETER_HOT)
                .build()
        );


    }

    @Test
    public void shouldBeAddDriverAndStoreWithMixPayment() {
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
        handler.handle(orderPayment);

        //Then
        driverCashPort.assertContains(DriverCash.builder()
                .id(1L)
                .cash(BigDecimal.valueOf(145))
                .groupId(1L)
                .driverId(2L)
                .prepaidCollectionCash(BigDecimal.valueOf(75))
                .clientId(20L)
                .build()
        );


        storeCollectionPort.assertContains(StoreCollection.builder()
                .storeId(3L)
                .pos(BigDecimal.valueOf(135))
                .cash(BigDecimal.valueOf(80))
                .groupId(1L)
                .clientId(20L)
                .tariffType(TariffType.TAXIMETER_HOT)
                .build()
        );

    }

}
