package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentRetrieveByDateRangeAndStore;
import com.maxijett.monetary.collectionreport.model.ShiftTime;
import com.maxijett.monetary.collectionreport.port.ShiftTimePort;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.common.util.MonetaryDate;
import lombok.RequiredArgsConstructor;

import java.time.*;
import java.util.List;

@DomainComponent
@RequiredArgsConstructor
public class GetAllCollectionPaymentsByDateBetweenAndStoreUseCaseHandler implements UseCaseHandler<List<CollectionPayment>, CollectionPaymentRetrieveByDateRangeAndStore> {

    private final CollectionPaymentPort collectionPaymentPort;

    private final ShiftTimePort shiftTimePort;

    @Override
    public List<CollectionPayment> handle(CollectionPaymentRetrieveByDateRangeAndStore useCase) {

        ShiftTime shiftTime = shiftTimePort.getShiftTime();

        ZonedDateTime dateRangeFrom = MonetaryDate.convertStartZonedDateTime(useCase.getFirstDate(), shiftTime.getNightShiftEndHour());
        ZonedDateTime dateRangeTo = MonetaryDate.convertEndZonedDateTime(useCase.getLastDate(), shiftTime.getNightShiftEndHour());

        return collectionPaymentPort.retrieveCollectionPaymentListByStoreIdAndDates(useCase.getStoreId(), dateRangeFrom, dateRangeTo);

    }
}
