package com.maxijett.monetary.collectionpayment.port;

import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentDelete;

public interface CollectionPaymentPort {

  CollectionPayment create(CollectionPaymentCreate collectionPaymentCreate);

  CollectionPayment retrieve(Long id);

  CollectionPayment update(Long id);

}
