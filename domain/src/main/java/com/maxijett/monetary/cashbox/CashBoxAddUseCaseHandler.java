package com.maxijett.monetary.cashbox;

import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.model.CashBoxTransaction;
import com.maxijett.monetary.cashbox.model.DriverCash;
import com.maxijett.monetary.cashbox.model.DriverPaymentTransaction;
import com.maxijett.monetary.cashbox.port.CashBoxPort;
import com.maxijett.monetary.cashbox.port.CashBoxTransactionPort;
import com.maxijett.monetary.cashbox.port.DriverCashPort;
import com.maxijett.monetary.cashbox.port.DriverPaymentTransactionPort;
import com.maxijett.monetary.cashbox.usecase.CashBoxAdd;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@DomainComponent
@RequiredArgsConstructor
public class CashBoxAddUseCaseHandler implements UseCaseHandler<CashBox, CashBoxAdd> {

    private final CashBoxPort cashBoxPort;
    private final DriverCashPort driverCashPort;
    private final DriverPaymentTransactionPort driverTransactionPort;
    private final CashBoxTransactionPort cashBoxTransactionPort;

    //        Cashbox amount increase for store chain
    //        CashboxTransaction a transaction record
    //        driver cash amount decrease( should be priority cold collection )
    //        Driver transaction record
    @Override
    public CashBox handle(CashBoxAdd useCase) {

        CashBox cashBox = cashBoxPort.retrive(useCase.getGroupId());

        cashBox.setCash(cashBox.getCash().add(useCase.getAmount()));

        cashBoxTransactionPort.createTransaction(buildCashBoxTransaction(useCase, cashBox));

        DriverCash driverCash = driverCashPort.retrieve(useCase.getDriverId());

        driverCash.setAmount(driverCash.getAmount().subtract(useCase.getAmount()));

        driverTransactionPort.createTransaction(buildDriverTransaction(useCase));

        return cashBox;
    }

    private DriverPaymentTransaction buildDriverTransaction(CashBoxAdd useCase) {
        return DriverPaymentTransaction.builder()
                .driverId(useCase.getDriverId())
                .paymentCash(useCase.getAmount())
                .dateTime(ZonedDateTime.now())
                .build();
    }

    private CashBoxTransaction buildCashBoxTransaction(CashBoxAdd useCase, CashBox cashBox) {
        return CashBoxTransaction.builder()
                .driverId(useCase.getDriverId())
                .totalCash(cashBox.getCash())
                .dateTime(ZonedDateTime.now())
                .amount(useCase.getAmount())
                .build();
    }
}
