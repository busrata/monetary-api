package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentMonthlyByStoreRetrieve;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@DomainComponent
@RequiredArgsConstructor
public class RetrieveBillingPaymentMonthlyByStoreUseCaseHandler implements UseCaseHandler<List<BillingPayment>, BillingPaymentMonthlyByStoreRetrieve> {

    private final BillingPaymentPort billingPaymentPort;

    @Override
    public List<BillingPayment> handle(BillingPaymentMonthlyByStoreRetrieve useCase) {

        ZonedDateTime requestDate = useCase.getRequestDate().atStartOfDay(ZoneOffset.UTC);

        return billingPaymentPort.retrieveBillingPaymentMonthlyByStore(useCase.getStoreId(), requestDate);
    }
}
