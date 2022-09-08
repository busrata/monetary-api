package com.maxijett.monetary.store.adapters;

import com.maxijett.monetary.store.GetStoreCollectionUseCaseHandler;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.usecase.StoreCollectionRetrieve;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetStoreCollectionUseCaseHandlerTest {

    StoreCollectionFakeDataAdapter storeCollectionPort;

    GetStoreCollectionUseCaseHandler handler;

    @BeforeEach
    public void setUp() {
        storeCollectionPort = new StoreCollectionFakeDataAdapter();
        handler = new GetStoreCollectionUseCaseHandler(storeCollectionPort);
    }


    @Test
    void shouldGetStoreCollection() {
        //Given
        StoreCollectionRetrieve storeCollectionRetrieve = StoreCollectionRetrieve.builder().id(3L).build();

        //When
        StoreCollection response = handler.handle(storeCollectionRetrieve);

        //Then
        Assertions.assertEquals(3, response.getStoreId());
    }

}
