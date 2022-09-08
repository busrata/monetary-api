package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.collectionpayment.adapters.CollectionPaymentFakeDataAdapter;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentDelete;
import com.maxijett.monetary.store.adapters.StoreCollectionFakeDataAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DeleteCollectionPaymentByStoreChainAdminUseCaseHandlerTest {

    CollectionPaymentPort collectionPaymentPort;
    StoreCollectionFakeDataAdapter storeCollectionPort;
    DeleteCollectionPaymentByStoreChainAdminUseCaseHandler useCaseHandler;

    @BeforeEach
    public void setUp() {
        collectionPaymentPort = new CollectionPaymentFakeDataAdapter();
        storeCollectionPort = new StoreCollectionFakeDataAdapter();
        useCaseHandler = new DeleteCollectionPaymentByStoreChainAdminUseCaseHandler(collectionPaymentPort, storeCollectionPort);
    }

    @Test
    public void shouldBeDeletedRecordTypeCollectionPaymentExistWhenCollectionPaymentDelete() {
        //Given
        CollectionPaymentDelete useCase = CollectionPaymentDelete.builder()
                .id(5L)
                .build();

        //When
        CollectionPayment responseCollectionPayment = useCaseHandler.handle(useCase);

        //Then
        assertEquals(5L, responseCollectionPayment.getId());
        assertEquals(Boolean.TRUE, responseCollectionPayment.getIsDeleted());

    }

    @Test
    public void shouldBePayBackStoreCollectionWhenCollectionPaymentDelete() {
        //Given
        CollectionPaymentDelete useCase = CollectionPaymentDelete.builder()
                .id(5L)
                .build();

        BigDecimal storeCollectionPosBefore = storeCollectionPort.retrieve(20L).getPos();
        BigDecimal collectionPaymentAmount = collectionPaymentPort.retrieve(5L).getPos();

        //When
        CollectionPayment responseCollectionPayment = useCaseHandler.handle(useCase);

        //Then
        assertEquals(5L, responseCollectionPayment.getId());
        assertEquals(Boolean.TRUE, responseCollectionPayment.getIsDeleted());


        assertEquals(storeCollectionPosBefore.add(collectionPaymentAmount), storeCollectionPort.storeCollectionList.get(0).getPos());

    }
}
