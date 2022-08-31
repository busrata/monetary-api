package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentDelete;
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
public class DeleteCollectionPaymentByStoreChainAdminUseCaseHandler implements UseCaseHandler<CollectionPayment, CollectionPaymentDelete> {

    private final CollectionPaymentPort collectionPaymentPort;
    private final StoreCollectionPort storeCollectionPort;

    @Override
    @Transactional
    public CollectionPayment handle(CollectionPaymentDelete useCase) {

        CollectionPayment collectionPayment = collectionPaymentPort.retrieve(useCase.getId());

        StoreCollection storeCollection = storeCollectionPort.retrieve(collectionPayment.getStoreId());
        storeCollection.setPos(storeCollection.getPos().add(collectionPayment.getPos()));

        storeCollectionPort.update(storeCollection, StorePaymentTransaction.builder()
                .pos(collectionPayment.getPos())
                .storeId(storeCollection.getStoreId())
                .eventType(StoreEventType.ADMIN_PAYMENT_BACK)
                .clientId(storeCollection.getClientId())
                .date(ZonedDateTime.now(ZoneId.of("UTC")))
                .build());
        return collectionPaymentPort.update(useCase.getId());

    }
}
