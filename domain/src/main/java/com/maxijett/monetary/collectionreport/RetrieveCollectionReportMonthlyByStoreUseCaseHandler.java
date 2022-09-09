package com.maxijett.monetary.collectionreport;

import com.maxijett.monetary.collectionreport.model.CollectionReport;
import com.maxijett.monetary.collectionreport.model.ShiftTime;
import com.maxijett.monetary.collectionreport.port.CollectionReportPort;
import com.maxijett.monetary.collectionreport.port.ShiftTimePort;
import com.maxijett.monetary.collectionreport.useCase.CollectionReportMonthlyByStoreRetrieve;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.common.util.MonetaryDate;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@DomainComponent
@RequiredArgsConstructor
public class RetrieveCollectionReportMonthlyByStoreUseCaseHandler implements UseCaseHandler<List<CollectionReport>, CollectionReportMonthlyByStoreRetrieve> {

    private final CollectionReportPort collectionReportPort;

    private final ShiftTimePort shiftTimePort;

    @Override
    public List<CollectionReport> handle(CollectionReportMonthlyByStoreRetrieve useCase) {

        ShiftTime shiftTime = shiftTimePort.getShiftTime();

        ZonedDateTime dateRangeFrom = MonetaryDate.convertStartZonedDateTime(useCase.getRequestDate(), shiftTime.getNightShiftEndHour());
        ZonedDateTime dateRangeTo = MonetaryDate.convertEndZonedDateTime(useCase.getRequestDate().plusMonths(1).minusDays(1), shiftTime.getNightShiftEndHour());

        return collectionReportPort.getListDateRangeByStore(useCase.getStoreId(), dateRangeFrom, dateRangeTo);
    }
}
