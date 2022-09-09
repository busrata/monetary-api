package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentListGet;
import com.maxijett.monetary.collectionreport.model.ShiftTime;
import com.maxijett.monetary.collectionreport.port.ShiftTimePort;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.common.util.MonetaryDate;
import lombok.RequiredArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@DomainComponent
public class GetBillingPaymentsByDateAndGroupIdUseCaseHandler implements UseCaseHandler<List<BillingPayment>, BillingPaymentListGet> {

    private final BillingPaymentPort billingPaymentPort;

    private final ShiftTimePort shiftTimePort;

    @Override
    public List<BillingPayment> handle(BillingPaymentListGet useCase) {

        ShiftTime shiftTime = shiftTimePort.getShiftTime();

        ZonedDateTime dateRangeFrom = MonetaryDate.convertStartZonedDateTime(useCase.getCreateOn(), shiftTime.getNightShiftEndHour());
        ZonedDateTime dateRangeTo = MonetaryDate.convertEndZonedDateTime(useCase.getCreateOn(), shiftTime.getNightShiftEndHour());

        return billingPaymentPort.getAllByGroupIdAndCreateOn(useCase.getGroupId(), dateRangeFrom, dateRangeTo);
    }
}
