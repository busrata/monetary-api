package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentRetrieveByDateRangeAndStore;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;

import java.time.*;
import java.util.List;

@DomainComponent
@RequiredArgsConstructor
public class GetAllCollectionPaymentsByStoreIdAndDateUseCaseHandler implements UseCaseHandler<List<CollectionPayment>, CollectionPaymentRetrieveByDateRangeAndStore> {

    private final CollectionPaymentPort collectionPaymentPort;

    @Override
    public List<CollectionPayment> handle(CollectionPaymentRetrieveByDateRangeAndStore useCase) {

        ZonedDateTime startTime = useCase.getFirstDate().atStartOfDay(ZoneOffset.UTC);
        ZonedDateTime endTime = useCase.getLastDate().atTime(23, 59, 59, 999999999).atZone(ZoneOffset.UTC);

        return collectionPaymentPort.retrieveCollectionPaymentListByStoreIdAndDates(useCase.getStoreId(),startTime, endTime);

    }
}
