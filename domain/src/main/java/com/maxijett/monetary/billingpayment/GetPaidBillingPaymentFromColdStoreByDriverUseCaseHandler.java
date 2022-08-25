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


    @Override
    public BillingPayment handle(BillingPaymentPrePaidCreate useCase) {
        DriverCash driverCash = driverCashPort.retrieve(useCase.getDriverId(), useCase.getGroupId());
        driverCash.setPrepaidCollectionCash(driverCash.getPrepaidCollectionCash().add(useCase.getPrePaidBillingAmount()));
        driverCashPort.update(driverCash, DriverPaymentTransaction.builder()
                .driverId(useCase.getDriverId())
                .paymentCash(useCase.getPrePaidBillingAmount())
                .eventType(DriverEventType.COLD_STORE_COLLECTION)
                .groupId(useCase.getGroupId())
                .dateTime(ZonedDateTime.now(ZoneId.of("UTC")))
            .build());


        return billingPaymentPort.create(useCase);
    }

}
