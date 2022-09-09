package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentDateBetweenByStoreRetrieve;
import com.maxijett.monetary.collectionreport.model.ShiftTime;
import com.maxijett.monetary.collectionreport.port.ShiftTimePort;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import java.time.ZonedDateTime;
import java.util.List;

import com.maxijett.monetary.common.util.MonetaryDate;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class RetrieveBillingPaymentListByDateBetweenAndStoreUseCaseHandler implements UseCaseHandler<List<BillingPayment>, BillingPaymentDateBetweenByStoreRetrieve> {

    private final BillingPaymentPort billingPaymentPort;

    private final ShiftTimePort shiftTimePort;

    @Override
    public List<BillingPayment> handle(BillingPaymentDateBetweenByStoreRetrieve useCase) {
        ShiftTime shiftTime = shiftTimePort.getShiftTime();

        ZonedDateTime dateRangeFrom = MonetaryDate.convertStartZonedDateTime(useCase.getStartDate(), shiftTime.getNightShiftEndHour());
        ZonedDateTime dateRangeTo = MonetaryDate.convertEndZonedDateTime(useCase.getEndDate(), shiftTime.getNightShiftEndHour());

        return billingPaymentPort.retrieveBillingPaymentListByDateBetweenAndStore(useCase.getStoreId(), dateRangeFrom, dateRangeTo);
    }
}
