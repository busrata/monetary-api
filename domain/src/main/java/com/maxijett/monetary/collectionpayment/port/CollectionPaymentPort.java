package com.maxijett.monetary.collectionpayment.port;

import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;

public interface CollectionPaymentPort {

  CollectionPayment create(CollectionPaymentCreate collectionPaymentCreate);

}
