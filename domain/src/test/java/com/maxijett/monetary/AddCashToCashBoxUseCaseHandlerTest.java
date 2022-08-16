package com.maxijett.monetary;

import com.maxijett.monetary.adapters.*;
import com.maxijett.monetary.cashbox.AddCashToCashBoxUseCaseHandler;
import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.model.CashBoxTransaction;
import com.maxijett.monetary.cashbox.model.DriverCash;
import com.maxijett.monetary.cashbox.port.CashBoxPort;
import com.maxijett.monetary.cashbox.port.CashBoxTransactionPort;
import com.maxijett.monetary.cashbox.port.DriverPaymentTransactionPort;
import com.maxijett.monetary.cashbox.usecase.CashBoxAdd;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddCashToCashBoxUseCaseHandlerTest {

    CashBoxPort mockCashBoxPort;
    DriverCashFakeDataAdapter driverCashFakeDataAdapter;
    DriverPaymentTransactionPort driverPaymentTransactionPort;
    CashBoxTransactionFakeDataAdapter cashBoxTransactionFakeDataAdapter;
    AddCashToCashBoxUseCaseHandler addCashToCashBoxUseCaseHandler;

    @BeforeEach
    public void setUp() {
        mockCashBoxPort = new CashBoxFakeDataAdapter();
        driverCashFakeDataAdapter = new DriverCashFakeDataAdapter();
        cashBoxTransactionFakeDataAdapter = new CashBoxTransactionFakeDataAdapter();
        driverPaymentTransactionPort = new DriverPaymentTransactionFakeDataAdapter();
        addCashToCashBoxUseCaseHandler = new AddCashToCashBoxUseCaseHandler(mockCashBoxPort, driverCashFakeDataAdapter, driverPaymentTransactionPort, cashBoxTransactionFakeDataAdapter);
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
        assertEquals(new BigDecimal(25.00), cashBoxTransactionFakeDataAdapter.getCashBoxTransactions().get(0).getAmount());

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
                        .dispatchDriverId(2L)
                        .clientId(20L)
                        .groupId(1L)
                        .prepaidCollectionCash(BigDecimal.valueOf(0))
                        .cash(BigDecimal.valueOf(20))
                        .build());

        assertEquals(BigDecimal.valueOf(25), cashBox.getCash());
        assertEquals(cashBox.getGroupId(), cashBoxAdd.getGroupId());

    }
}
