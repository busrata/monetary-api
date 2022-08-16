package com.maxijett.monetary.cashbox;

import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.model.CashBoxTransaction;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import com.maxijett.monetary.cashbox.port.*;
import com.maxijett.monetary.cashbox.usecase.CashBoxAdd;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.driver.port.DriverCashPort;
import com.maxijett.monetary.driver.port.DriverPaymentTransactionPort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@DomainComponent
@RequiredArgsConstructor
public class AddCashToCashBoxUseCaseHandler implements UseCaseHandler<CashBox, CashBoxAdd> {

    private final CashBoxPort cashBoxPort;
    private final DriverCashPort driverCashPort;
    private final DriverPaymentTransactionPort driverPaymentTransactionPort;
    private final CashBoxTransactionPort cashBoxTransactionPort;

    @Override
    public CashBox handle(CashBoxAdd useCase) {

        CashBox cashBox = cashBoxPort.retrieve(useCase.getGroupId());
        DriverCash driverCash = driverCashPort.retrieve(useCase.getDriverId(), useCase.getGroupId());

        if (useCase.getAmount().compareTo(driverCash.getPrepaidCollectionCash()) > 0) {

            BigDecimal increaseAmount = useCase.getAmount().subtract(driverCash.getPrepaidCollectionCash());
            driverCash.setCash(driverCash.getCash().subtract(useCase.getAmount()));

            driverPaymentTransactionPort.createTransaction(buildDriverTransaction(useCase, DriverEventType.ADMIN_GET_PAID));

            cashBox.setCash(cashBox.getCash().add(increaseAmount));

            driverCash.setPrepaidCollectionCash(BigDecimal.valueOf(0));

            cashBoxPort.update(cashBox);

            cashBoxTransactionPort.createTransaction(buildCashBoxTransaction(useCase, increaseAmount));


        } else {
            driverCash.setPrepaidCollectionCash(driverCash.getPrepaidCollectionCash().subtract(useCase.getAmount()));
            driverCash.setCash(driverCash.getCash().subtract(useCase.getAmount()));
            driverPaymentTransactionPort.createTransaction(buildDriverTransaction(useCase, DriverEventType.COLD_STORE_COLLECTION));
        }


        driverCashPort.update(driverCash);

        return cashBox;
    }

    private DriverPaymentTransaction buildDriverTransaction(CashBoxAdd useCase, DriverEventType eventType) {
        return DriverPaymentTransaction.builder()
                .driverId(useCase.getDriverId())
                .paymentCash(useCase.getAmount())
                .dateTime(ZonedDateTime.now())
                .eventType(eventType)
                .build();
    }

    private CashBoxTransaction buildCashBoxTransaction(CashBoxAdd useCase, BigDecimal increaseAmount) {
        return CashBoxTransaction.builder()
                .driverId(useCase.getDriverId())
                .dateTime(ZonedDateTime.now())
                .amount(increaseAmount)
                .build();
    }
}
