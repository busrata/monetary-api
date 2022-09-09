package com.maxijett.monetary.cashbox;

import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.model.CashBoxTransaction;
import com.maxijett.monetary.cashbox.model.enumaration.CashBoxEventType;
import com.maxijett.monetary.cashbox.port.CashBoxPort;
import com.maxijett.monetary.cashbox.usecase.CashBoxAdd;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import com.maxijett.monetary.driver.port.DriverCashPort;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@DomainComponent
@RequiredArgsConstructor
public class AddCashToCashBoxUseCaseHandler implements UseCaseHandler<CashBox, CashBoxAdd> {

    private final CashBoxPort cashBoxPort;
    private final DriverCashPort driverCashPort;

    private static final String CASHBOX = "cashBox";

    @Transactional
    @Override
    public CashBox handle(CashBoxAdd useCase) {

        CashBox cashBox = cashBoxPort.retrieve(useCase.getGroupId());
        DriverCash driverCash = driverCashPort.retrieve(useCase.getDriverId(), useCase.getGroupId());

        if (useCase.getAmount().compareTo(driverCash.getPrepaidCollectionCash()) > 0) {

            BigDecimal increaseAmount = useCase.getAmount().subtract(driverCash.getPrepaidCollectionCash());
            driverCash.setCash(driverCash.getCash().subtract(useCase.getAmount()));


            cashBox.setCash(cashBox.getCash().add(increaseAmount));

            driverCash.setPrepaidCollectionCash(BigDecimal.valueOf(0));

            cashBoxPort.update(cashBox, buildCashBoxTransaction(useCase));


        } else {
            driverCash.setPrepaidCollectionCash(driverCash.getPrepaidCollectionCash().subtract(useCase.getAmount()));
            driverCash.setCash(driverCash.getCash().subtract(useCase.getAmount()));
        }


        driverCashPort.update(driverCash, buildDriverPaymentTransaction(useCase));

        return cashBox;
    }

    private DriverPaymentTransaction buildDriverPaymentTransaction(CashBoxAdd useCase) {
        return DriverPaymentTransaction.builder()
                .eventType(DriverEventType.ADMIN_GET_PAID)
                .driverId(useCase.getDriverId())
                .groupId(useCase.getGroupId())
                .dateTime(ZonedDateTime.now(ZoneOffset.UTC))
                .paymentCash(useCase.getAmount())
                .clientId(useCase.getClientId())
                .build();
    }

    private CashBoxTransaction buildCashBoxTransaction(CashBoxAdd useCase) {
        return CashBoxTransaction.builder()
                .driverId(useCase.getDriverId())
                .dateTime(ZonedDateTime.now(ZoneOffset.UTC))
                .payingAccount(useCase.getPayingAccount())
                .amount(useCase.getAmount())
                .cashBoxEventType(useCase.getPayingAccount().equals(CASHBOX) ? CashBoxEventType.DRIVER_PAY : CashBoxEventType.ADMIN_PAY)
                .build();
    }
}
