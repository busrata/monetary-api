package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentMonthlyByStoreRetrieve;
import com.maxijett.monetary.collectionreport.model.ShiftTime;
import com.maxijett.monetary.collectionreport.port.ShiftTimePort;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.common.util.MonetaryDate;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@DomainComponent
@RequiredArgsConstructor
public class RetrieveBillingPaymentMonthlyByStoreUseCaseHandler implements UseCaseHandler<List<BillingPayment>, BillingPaymentMonthlyByStoreRetrieve> {

    private final BillingPaymentPort billingPaymentPort;

    private final ShiftTimePort shiftTimePort;

    @Override
    public List<BillingPayment> handle(BillingPaymentMonthlyByStoreRetrieve useCase) {

        ShiftTime shiftTime = shiftTimePort.getShiftTime();

        ZonedDateTime dateRangeFrom = MonetaryDate.convertStartZonedDateTime(useCase.getRequestDate(), shiftTime.getNightShiftEndHour());
        ZonedDateTime dateRangeTo = MonetaryDate.convertEndZonedDateTime(useCase.getRequestDate().plusMonths(1).minusDays(1), shiftTime.getNightShiftEndHour());

        return billingPaymentPort.retrieveBillingPaymentListByDateBetweenAndStore(useCase.getStoreId(), dateRangeFrom, dateRangeTo);
    }
}
