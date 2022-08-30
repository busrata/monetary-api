package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.billingpayment.DeleteCollectionPaymentByStoreChainAdminUseCaseHandler;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentDelete;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class DeleteCollectionPaymentByStoreChainAdminUseCaseHandlerTest {
    DeleteCollectionPaymentByStoreChainAdminUseCaseHandler useCaseHandler = new DeleteCollectionPaymentByStoreChainAdminUseCaseHandler();

    @Test
    public void shouldBeDeletedRecordTypeCollectionPaymentExistWhenCollectionPaymentDelete(){
        //Given
        CollectionPaymentDelete useCase = CollectionPaymentDelete.builder()
                .id(5L)
                .build();
        //When
        CollectionPayment responseCollectionPayment= useCaseHandler.handle(useCase);

        //Then
        assertEquals(Boolean.TRUE,responseCollectionPayment);
        assertEquals(5L, responseCollectionPayment.getId());
        assertEquals(Boolean.TRUE,responseCollectionPayment.getIsDeleted());


    }
}
