package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentPrePaidCreate;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import com.maxijett.monetary.driver.port.DriverCashPort;
import com.maxijett.monetary.driver.port.DriverPaymentTransactionPort;
import lombok.RequiredArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@DomainComponent
@RequiredArgsConstructor
public class GetPaidBillingPaymentFromColdStoreByDriverUseCaseHandler implements
        UseCaseHandler<BillingPayment, BillingPaymentPrePaidCreate> {

    private final BillingPaymentPort billingPaymentPort;

    private final DriverCashPort driverCashPort;

    private final DriverPaymentTransactionPort driverPaymentTransactionPort;

    @Override
    public BillingPayment handle(BillingPaymentPrePaidCreate useCase) {
        DriverCash driverCash = driverCashPort.retrieve(useCase.getDriverId(), useCase.getGroupId());
        driverCash.setPrepaidCollectionCash(driverCash.getPrepaidCollectionCash().add(useCase.getPrePaidBillingAmount()));
        driverCashPort.update(driverCash);

        driverPaymentTransactionPort.createTransaction(buildDriverPaymentTransaction(useCase));

        return billingPaymentPort.create(useCase);
    }

    private DriverPaymentTransaction buildDriverPaymentTransaction(BillingPaymentPrePaidCreate billingPaymentPrePaidCreate) {
        return DriverPaymentTransaction.builder()
                .driverId(billingPaymentPrePaidCreate.getDriverId())
                .paymentCash(billingPaymentPrePaidCreate.getPrePaidBillingAmount())
                .dateTime(ZonedDateTime.now(ZoneId.of("UTC")))
                .eventType(DriverEventType.COLD_STORE_COLLECTION)
                .groupId(billingPaymentPrePaidCreate.getGroupId())
                .build();
    }
}
