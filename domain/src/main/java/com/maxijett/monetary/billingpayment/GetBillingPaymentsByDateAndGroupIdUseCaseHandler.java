package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentListGet;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@DomainComponent
public class GetBillingPaymentsByDateAndGroupIdUseCaseHandler implements UseCaseHandler<List<BillingPayment>, BillingPaymentListGet> {

    private final BillingPaymentPort billingPaymentPort;

    @Override
    public List<BillingPayment> handle(BillingPaymentListGet useCase) {
        ZonedDateTime startTime = useCase.getCreateOn().toLocalDate().atStartOfDay(ZoneId.of("UTC"));
        ZonedDateTime endTime = startTime.toLocalDate().atTime(23, 59, 59).atZone(ZoneId.of("UTC"));
        return billingPaymentPort.getAllByGroupIdAndCreateOn(useCase.getGroupId(), startTime, endTime);
    }
}
