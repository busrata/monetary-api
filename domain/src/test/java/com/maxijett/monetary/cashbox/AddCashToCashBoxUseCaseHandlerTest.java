package com.maxijett.monetary.cashbox;

import com.maxijett.monetary.cashbox.adapters.CashBoxFakeDataAdapter;
import com.maxijett.monetary.cashbox.adapters.CashBoxTransactionFakeDataAdapter;
import com.maxijett.monetary.cashbox.adapters.DriverCashFakeDataAdapter;
import com.maxijett.monetary.cashbox.adapters.DriverPaymentTransactionFakeDataAdapter;
import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.port.CashBoxPort;
import com.maxijett.monetary.cashbox.usecase.CashBoxAdd;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddCashToCashBoxUseCaseHandlerTest {

    CashBoxPort mockCashBoxPort;
    DriverCashFakeDataAdapter driverCashFakeDataAdapter;
    DriverPaymentTransactionFakeDataAdapter driverPaymentTransactionFakeDataAdapter;
    CashBoxTransactionFakeDataAdapter cashBoxTransactionFakeDataAdapter;
    AddCashToCashBoxUseCaseHandler addCashToCashBoxUseCaseHandler;

    @BeforeEach
    public void setUp() {
        mockCashBoxPort = new CashBoxFakeDataAdapter();
        driverCashFakeDataAdapter = new DriverCashFakeDataAdapter();
        cashBoxTransactionFakeDataAdapter = new CashBoxTransactionFakeDataAdapter();
        driverPaymentTransactionFakeDataAdapter = new DriverPaymentTransactionFakeDataAdapter();
        addCashToCashBoxUseCaseHandler = new AddCashToCashBoxUseCaseHandler(mockCashBoxPort, driverCashFakeDataAdapter, driverPaymentTransactionFakeDataAdapter, cashBoxTransactionFakeDataAdapter);
    }

    @Test
    public void addCashBoxUseCaseHandlerFromDriver() {

        //Given
        CashBoxAdd cashBoxAdd = CashBoxAdd.builder().driverId(1L)
                .payingAccount("StoreChainTest")
                .amount(BigDecimal.valueOf(100))
                .clientId(20000L).groupId(2L)
                .createOn(ZonedDateTime.now()).build();

        //When
        CashBox cashBox = addCashToCashBoxUseCaseHandler.handle(cashBoxAdd);

        //Then
        assertEquals(BigDecimal.valueOf(25), cashBox.getCash());
        assertEquals(cashBox.getGroupId(), cashBoxAdd.getGroupId());
        assertEquals(cashBoxAdd.getDriverId(), cashBoxTransactionFakeDataAdapter.getCashBoxTransactions().get(0).getDriverId());
        assertEquals(BigDecimal.valueOf(25), cashBoxTransactionFakeDataAdapter.getCashBoxTransactions().get(0).getAmount());

    }

    @Test
    public void shouldBeDecreasedPrepaidCollectionAmountFromCashBoxWhenPrepaidCollectionExist() {

        //Given
        CashBoxAdd cashBoxAdd = CashBoxAdd.builder()
                .driverId(1L)
                .payingAccount("StoreChainTest")
                .amount(BigDecimal.valueOf(100))
                .clientId(20000L).groupId(2L)
                .createOn(ZonedDateTime.now()).build();

        //When
        CashBox cashBox = addCashToCashBoxUseCaseHandler.handle(cashBoxAdd);

        //Then
        driverCashFakeDataAdapter.assertContains(
                DriverCash.builder()
                        .id(1L)
                        .driverId(2L)
                        .clientId(20L)
                        .groupId(1L)
                        .prepaidCollectionCash(BigDecimal.valueOf(0))
                        .cash(BigDecimal.valueOf(20))
                        .build());

        assertEquals(BigDecimal.valueOf(25), cashBox.getCash());
        assertEquals(cashBox.getGroupId(), cashBoxAdd.getGroupId());

    }

    @Test
    public void shouldBeCashNotAddToCashBoxWhenAmountLessThenPrepaidCollection() {

        //Given
        CashBoxAdd cashBoxAdd = CashBoxAdd.builder()
                .driverId(1L)
                .payingAccount("StoreChainTest")
                .amount(BigDecimal.valueOf(60))
                .clientId(20000L).groupId(2L)
                .createOn(ZonedDateTime.now()).build();

        //When
        CashBox cashBox = addCashToCashBoxUseCaseHandler.handle(cashBoxAdd);

        //Then
        driverCashFakeDataAdapter.assertContains(
                DriverCash.builder()
                        .id(1L)
                        .driverId(2L)
                        .clientId(20L)
                        .groupId(1L)
                        .prepaidCollectionCash(BigDecimal.valueOf(15))
                        .cash(BigDecimal.valueOf(60))
                        .build());

        assertEquals(BigDecimal.valueOf(0), cashBox.getCash());
        assertEquals(cashBox.getGroupId(), cashBoxAdd.getGroupId());
        assertEquals(cashBoxAdd.getDriverId(), driverPaymentTransactionFakeDataAdapter.getDriverPaymentTransactions().get(0).getDriverId());
        assertEquals(BigDecimal.valueOf(60), driverPaymentTransactionFakeDataAdapter.getDriverPaymentTransactions().get(0).getPaymentCash());
        assertEquals(DriverEventType.COLD_STORE_COLLECTION, driverPaymentTransactionFakeDataAdapter.getDriverPaymentTransactions().get(0).getEventType());

    }
}
