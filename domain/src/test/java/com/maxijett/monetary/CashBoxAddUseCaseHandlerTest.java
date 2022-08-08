package com.maxijett.monetary;

import com.maxijett.monetary.adapters.CashBoxFakeDataAdapter;
import com.maxijett.monetary.adapters.CashBoxTransactionFakeDataAdapter;
import com.maxijett.monetary.adapters.DriverCashFakeDataAdapter;
import com.maxijett.monetary.adapters.DriverTransactionFakeDataAdapter;
import com.maxijett.monetary.cashbox.CashBoxAddUseCaseHandler;
import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.port.CashBoxPort;
import com.maxijett.monetary.cashbox.port.CashBoxTransactionPort;
import com.maxijett.monetary.cashbox.port.DriverCashPort;
import com.maxijett.monetary.cashbox.port.DriverTransactionPort;
import com.maxijett.monetary.cashbox.usecase.CashBoxAdd;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

//        Cashbox amount increase for store chain
//        CashboxTransaction a transaction record
//        driver cash amount decrease
//        Driver transaction record
public class CashBoxAddUseCaseHandlerTest {

    CashBoxPort mockCashBoxPort;
    DriverCashPort driverCashPort;
    DriverTransactionPort driverTransactionPort;
    CashBoxTransactionPort mockCashBoxTransactionPort;
    CashBoxAddUseCaseHandler cashBoxAddUseCaseHandler;

    @BeforeEach
    public void setUp() {
        mockCashBoxPort = new CashBoxFakeDataAdapter();
        mockCashBoxTransactionPort = new CashBoxTransactionFakeDataAdapter();
        driverCashPort = new DriverCashFakeDataAdapter();
        driverTransactionPort = new DriverTransactionFakeDataAdapter();
        cashBoxAddUseCaseHandler = new CashBoxAddUseCaseHandler(mockCashBoxPort, driverCashPort, driverTransactionPort, mockCashBoxTransactionPort);
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
        CashBox cashBox = cashBoxAddUseCaseHandler.handle(cashBoxAdd);

        //Then
        assertEquals(BigDecimal.valueOf(100), cashBox.getCash());
        assertEquals(cashBox.getGroupId(), cashBoxAdd.getGroupId());

    }
}
