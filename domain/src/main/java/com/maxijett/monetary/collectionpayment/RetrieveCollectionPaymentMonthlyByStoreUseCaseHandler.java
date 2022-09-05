package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.StoreCollectionPaymentRetrieve;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@DomainComponent
@RequiredArgsConstructor
public class RetrieveCollectionPaymentMonthlyByStoreUseCaseHandler implements UseCaseHandler<List<CollectionPayment>, StoreCollectionPaymentRetrieve> {

    private final CollectionPaymentPort collectionPaymentPort;

    @Override
    public List<CollectionPayment> handle(StoreCollectionPaymentRetrieve useCase) {
        ZonedDateTime requestDate = useCase.getRequestDate().atStartOfDay(ZoneOffset.UTC);
        return collectionPaymentPort.retrieveCollectionPaymentMonthlyByStore(useCase.getStoreId(), requestDate);
    }
}
