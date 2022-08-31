package com.maxijett.monetary.collectionpayment.port;

import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;

import java.time.ZonedDateTime;
import java.util.List;

public interface CollectionPaymentPort {

    CollectionPayment create(CollectionPaymentCreate collectionPaymentCreate);

    List<CollectionPayment> retrieveCollectionPayments(Long driverId, Long groupId, ZonedDateTime startTime, ZonedDateTime endTime);

}
