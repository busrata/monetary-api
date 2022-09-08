package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.PaidToTheStoreCollectionPaymentRetrieve;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@DomainComponent
@RequiredArgsConstructor
public class PaidToTheStoreCollectionPaymentRetrieveUseCaseHandler implements UseCaseHandler<List<CollectionPayment>, PaidToTheStoreCollectionPaymentRetrieve> {

    private final CollectionPaymentPort collectionPaymentPort;

    @Override
    public List<CollectionPayment> handle(PaidToTheStoreCollectionPaymentRetrieve useCase) {

        ZonedDateTime startTime = useCase.getStartDate().atStartOfDay(ZoneOffset.UTC);
        ZonedDateTime endTime = useCase.getEndDate().atTime(23, 59, 59, 999999999).atZone(ZoneOffset.UTC);

        return collectionPaymentPort.retrieveCollectionPayments(useCase.getDriverId(), useCase.getGroupId(), startTime, endTime);
    }
}
