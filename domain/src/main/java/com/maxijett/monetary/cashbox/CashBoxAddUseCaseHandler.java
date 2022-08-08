package com.maxijett.monetary.cashbox;

import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.model.CashBoxTransaction;
import com.maxijett.monetary.cashbox.model.DriverCash;
import com.maxijett.monetary.cashbox.model.DriverTransaction;
import com.maxijett.monetary.cashbox.port.CashBoxPort;
import com.maxijett.monetary.cashbox.port.CashBoxTransactionPort;
import com.maxijett.monetary.cashbox.port.DriverCashPort;
import com.maxijett.monetary.cashbox.port.DriverTransactionPort;
import com.maxijett.monetary.cashbox.usecase.CashBoxAdd;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;


@RequiredArgsConstructor
public class CashBoxAddUseCaseHandler implements UseCaseHandler<CashBox, CashBoxAdd> {

    private final CashBoxPort cashBoxPort;
    private final DriverCashPort driverCashPort;
    private final DriverTransactionPort driverTransactionPort;
    private final CashBoxTransactionPort cashBoxTransactionPort;

    @Override
    public CashBox handle(CashBoxAdd useCase) {
        //        Cashbox amount increase for store chain
        //        CashboxTransaction a transaction record
        //        driver cash amount decrease( should be priority cold collection )
        //        Driver transaction record

        CashBox cashBox = cashBoxPort.retrive(useCase.getGroupId());

        cashBox.setCash(cashBox.getCash().add(useCase.getAmount()));

        cashBoxTransactionPort.createTransaction(buildCashBoxTransaction(useCase, cashBox));

        DriverCash driverCash = driverCashPort.retrieve(useCase.getDriverId());

        driverCash.setAmount(driverCash.getAmount().subtract(useCase.getAmount()));

        driverTransactionPort.createTransaction(buildDriverTransaction(useCase));

        return cashBox;
    }

    private DriverTransaction buildDriverTransaction(CashBoxAdd useCase) {
        return DriverTransaction.builder()
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
