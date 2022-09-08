package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import com.maxijett.monetary.store.port.StoreCollectionPort;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@DomainComponent
@RequiredArgsConstructor
public class PayCollectionPaymentToStoreByStoreChainAdminUseCaseHandler implements
        UseCaseHandler<CollectionPayment, CollectionPaymentCreate> {


    private final CollectionPaymentPort collectionPaymentPort;

    private final StoreCollectionPort storeCollectionPort;


    @Override
    @Transactional
    public CollectionPayment handle(CollectionPaymentCreate useCase) {

        CollectionPayment collectionPayment = collectionPaymentPort.create(useCase);

        StoreCollection storeCollection = storeCollectionPort.retrieve(useCase.getStoreId());

        storeCollection.setPos(storeCollection.getPos().subtract(useCase.getPos()));

        storeCollectionPort.update(storeCollection, StorePaymentTransaction.builder()
                .storeId(useCase.getStoreId())
                .createOn(ZonedDateTime.now(ZoneId.of("UTC")))
                .cash(useCase.getCash())
                .pos(useCase.getPos())
                .eventType(StoreEventType.ADMIN_GET_PAID)
                .clientId(useCase.getClientId())
                .build());


        return collectionPayment;
    }
}
