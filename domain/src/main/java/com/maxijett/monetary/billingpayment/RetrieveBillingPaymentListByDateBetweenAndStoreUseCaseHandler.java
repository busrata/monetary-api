package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentDateBetweenByStoreRetrieve;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class RetrieveBillingPaymentListByDateBetweenAndStoreUseCaseHandler implements UseCaseHandler<List<BillingPayment>, BillingPaymentDateBetweenByStoreRetrieve> {

    private final BillingPaymentPort billingPaymentPort;

    @Override
    public List<BillingPayment> handle(BillingPaymentDateBetweenByStoreRetrieve useCase) {
        ZonedDateTime startDate = useCase.getStartDate().atStartOfDay(ZoneOffset.UTC);

        ZonedDateTime endDate = useCase.getEndDate().atTime(23, 59, 59, 999999999).atZone(ZoneId.of("UTC"));

        return billingPaymentPort.retrieveBillingPaymentListByDateBetweenAndStore(useCase.getStoreId(), startDate, endDate);
    }
}
