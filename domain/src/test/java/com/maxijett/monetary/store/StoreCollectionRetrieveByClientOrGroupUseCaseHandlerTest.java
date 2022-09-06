package com.maxijett.monetary.store;

import com.maxijett.monetary.common.exception.MonetaryApiBusinessException;
import com.maxijett.monetary.store.adapters.StoreCollectionFakeDataAdapter;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.usecase.StoreCollectionRetrieve;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class StoreCollectionRetrieveByClientOrGroupUseCaseHandlerTest {

    StoreCollectionFakeDataAdapter storeCollectionPort;

    StoreCollectionRetrieveByClientOrGroupUseCaseHandler handler;

    @BeforeEach
    public void setUp() {
        storeCollectionPort = new StoreCollectionFakeDataAdapter();
        handler = new StoreCollectionRetrieveByClientOrGroupUseCaseHandler(storeCollectionPort);

    }

    @Test
    public void shouldBeRetrieveStoreCollectionByClientId() {
        //Given
        StoreCollectionRetrieve storeCollectionRetrieve = StoreCollectionRetrieve.builder()
                .clientId(20L)
                .build();

        //When
        List<StoreCollection> responseList = handler.handle(storeCollectionRetrieve);

        //Then
        Assertions.assertNotNull(responseList);
        Assertions.assertEquals(3, responseList.size());

        assertThat(responseList).isNotNull().hasSize(3)
                .extracting("storeId", "cash", "pos", "clientId")
                .containsExactlyInAnyOrder(
                        tuple(1L, BigDecimal.valueOf(55), BigDecimal.valueOf(100), 20L),
                        tuple(2L, BigDecimal.valueOf(65), BigDecimal.valueOf(90), 20L),
                        tuple(3L, BigDecimal.valueOf(75), BigDecimal.valueOf(85), 20L)
                );

    }

    @Test
    public void shouldBeRetrieveStoreCollectionByGroupId() {
        //Given
        StoreCollectionRetrieve storeCollectionRetrieve = StoreCollectionRetrieve.builder()
                .groupId(1L)
                .build();

        //When
        List<StoreCollection> responseList = handler.handle(storeCollectionRetrieve);

        //Then
        Assertions.assertNotNull(responseList);
        Assertions.assertEquals(3, responseList.size());

        assertThat(responseList).isNotNull().hasSize(3)
                .extracting("storeId", "cash", "pos", "groupId")
                .containsExactlyInAnyOrder(
                        tuple(1L, BigDecimal.valueOf(55), BigDecimal.valueOf(100), 1L),
                        tuple(2L, BigDecimal.valueOf(65), BigDecimal.valueOf(90), 1L),
                        tuple(3L, BigDecimal.valueOf(75), BigDecimal.valueOf(85), 1L)
                );

    }

    @Test
    public void failclientIdAndGroupIdCanNotBeNull() {
        //Given
        StoreCollectionRetrieve storeCollectionRetrieve = StoreCollectionRetrieve.builder().build();

        //When & Then
        assertThatExceptionOfType(MonetaryApiBusinessException.class)
                .isThrownBy(() -> handler.handle(storeCollectionRetrieve))
                .withMessage("monetaryapi.cashbox.clientIdAndGroupIdCanNotBeNull");
    }

}
