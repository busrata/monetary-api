package com.maxijett.monetary.cashbox;

import com.maxijett.monetary.cashbox.adapters.CashBoxFakeDataAdapter;
import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.port.CashBoxPort;
import com.maxijett.monetary.cashbox.usecase.CashBoxAdd;
import com.maxijett.monetary.driver.adapters.DriverCashFakeDataAdapter;
import com.maxijett.monetary.driver.model.DriverCash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddCashToCashBoxUseCaseHandlerTest {

    CashBoxPort mockCashBoxPort;
    DriverCashFakeDataAdapter driverCashFakeDataAdapter;
    AddCashToCashBoxUseCaseHandler addCashToCashBoxUseCaseHandler;

    @BeforeEach
    public void setUp() {
        mockCashBoxPort = new CashBoxFakeDataAdapter();
        driverCashFakeDataAdapter = new DriverCashFakeDataAdapter();

        addCashToCashBoxUseCaseHandler = new AddCashToCashBoxUseCaseHandler(mockCashBoxPort, driverCashFakeDataAdapter);
    }

    @Test
    public void addCashBoxUseCaseHandlerFromDriver() {

        //Given
        CashBoxAdd cashBoxAdd = CashBoxAdd.builder().driverId(1L)
                .payingAccount("cashBox")
                .amount(BigDecimal.valueOf(100))
                .clientId(20000L).groupId(2L)
                .createOn(ZonedDateTime.now()).build();

        //When
        CashBox cashBox = addCashToCashBoxUseCaseHandler.handle(cashBoxAdd);

        //Then
        assertEquals(BigDecimal.valueOf(275), cashBox.getCash());
        assertEquals(cashBox.getGroupId(), cashBoxAdd.getGroupId());

    }

    @Test
    public void addCashBoxUseCaseHandlerFromStoreChainAdmin() {

        //Given
        CashBoxAdd cashBoxAdd = CashBoxAdd.builder().driverId(1L)
                .payingAccount("storeChainAdmin")
                .amount(BigDecimal.valueOf(90))
                .clientId(20000L).groupId(2L)
                .createOn(ZonedDateTime.now()).build();

        //When
        CashBox cashBox = addCashToCashBoxUseCaseHandler.handle(cashBoxAdd);

        //Then
        assertEquals(BigDecimal.valueOf(265), cashBox.getCash());
        assertEquals(cashBox.getGroupId(), cashBoxAdd.getGroupId());

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

        assertEquals(BigDecimal.valueOf(275), cashBox.getCash());
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

        assertEquals(BigDecimal.valueOf(250), cashBox.getCash());
        assertEquals(cashBox.getGroupId(), cashBoxAdd.getGroupId());
    }
}
