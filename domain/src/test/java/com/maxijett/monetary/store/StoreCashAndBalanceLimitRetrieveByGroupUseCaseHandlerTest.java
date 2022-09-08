package com.maxijett.monetary.store;

import com.maxijett.monetary.store.adapters.StoreCollectionFakeDataAdapter;
import com.maxijett.monetary.store.model.StoreCashAndBalanceLimit;
import com.maxijett.monetary.store.useCase.StoreCollectionRetrieve;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class StoreCashAndBalanceLimitRetrieveByGroupUseCaseHandlerTest {

    StoreCollectionFakeDataAdapter storeCollectionPort;

    StoreCashAndBalanceLimitRetrieveByGroupUseCaseHandler handler;

    @BeforeEach
    public void setUp() {
        storeCollectionPort = new StoreCollectionFakeDataAdapter();
        handler = new StoreCashAndBalanceLimitRetrieveByGroupUseCaseHandler(storeCollectionPort);

    }

    @Test
    public void shouldBeRetrieveCashAndBalanceLimitByGroupId() {
        //Given
        StoreCollectionRetrieve storeCollectionRetrieve = StoreCollectionRetrieve.builder()
                .groupId(1L)
                .build();

        //When
        List<StoreCashAndBalanceLimit> responseList = handler.handle(storeCollectionRetrieve);

        //Then
        assertThat(responseList).isNotNull().hasSize(3)
                .extracting("storeId", "cash", "balanceLimit")
                .containsExactlyInAnyOrder(
                        tuple(1L, BigDecimal.valueOf(55), BigDecimal.valueOf(45)),
                        tuple(2L, BigDecimal.valueOf(65), BigDecimal.valueOf(55)),
                        tuple(3L, BigDecimal.valueOf(75), BigDecimal.valueOf(65))
                );

    }

}
