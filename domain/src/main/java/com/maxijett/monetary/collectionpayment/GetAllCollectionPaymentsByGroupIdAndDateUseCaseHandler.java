package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentListGet;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainComponent
public class GetAllCollectionPaymentsByGroupIdAndDateUseCaseHandler implements UseCaseHandler<List<CollectionPayment>, CollectionPaymentListGet> {

    private final CollectionPaymentPort collectionPaymentPort;

    @Override
    public List<CollectionPayment> handle(CollectionPaymentListGet useCase){

        ZonedDateTime startTime = useCase.getCreateOn().toLocalDate().atStartOfDay(ZoneId.of("UTC"));
        ZonedDateTime endTime = startTime.toLocalDate().atTime(23,59,59).atZone(ZoneId.of("UTC"));

        return collectionPaymentPort.retrieveCollectionPaymentListByGroupIdAndDates(
            useCase.getGroupId(), startTime, endTime);
    }
}
