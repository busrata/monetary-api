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
import com.maxijett.monetary.store.port.StorePaymentTransactionPort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@DomainComponent
@RequiredArgsConstructor
public class PayCollectionPaymentToStoreByStoreChainAdminUseCaseHandler implements
        UseCaseHandler<CollectionPayment, CollectionPaymentCreate> {


    private final CollectionPaymentPort collectionPaymentPort;

    private final StoreCollectionPort storeCollectionPort;

    private final StorePaymentTransactionPort storePaymentTransactionPort;

    @Override
    public CollectionPayment handle(CollectionPaymentCreate useCase) {

        CollectionPayment collectionPayment = collectionPaymentPort.create(useCase);

        StoreCollection storeCollection = storeCollectionPort.retrieve(useCase.getStoreId());

        storeCollection.setPos(storeCollection.getPos().subtract(useCase.getPos()));

        storeCollectionPort.update(storeCollection);

        StorePaymentTransaction storePaymentTransaction = StorePaymentTransaction.builder()
                .storeId(collectionPayment.getStoreId())
                .date(ZonedDateTime.now(ZoneId.of("UTC")))
                .clientId(collectionPayment.getClientId())
                .cash(BigDecimal.ZERO)
                .pos(useCase.getPos())
                .eventType(StoreEventType.ADMIN_GET_PAID)
                .build();

        storePaymentTransactionPort.create(storePaymentTransaction);

        return collectionPayment;
    }
}
