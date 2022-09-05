package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.collectionpayment.adapters.CollectionPaymentFakeDataAdapter;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.useCase.StoreCollectionPaymentRetrieve;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class RetrieveCollectionPaymentMonthlyByStoreUseCaseHandlerTest {

    CollectionPaymentFakeDataAdapter collectionPaymentPort = new CollectionPaymentFakeDataAdapter();
    RetrieveCollectionPaymentMonthlyByStoreUseCaseHandler handler = new RetrieveCollectionPaymentMonthlyByStoreUseCaseHandler(collectionPaymentPort);

    @Test
    public void shouldReturnMonthlyCollectionPaymentListByStore() {
        //Given
        StoreCollectionPaymentRetrieve useCase = StoreCollectionPaymentRetrieve.builder()
                .storeId(20L)
                .requestDate(LocalDate.now())
                .build();

        //When
        List<CollectionPayment> collectionPaymentList = handler.handle(useCase);

        //Then
        assertThat(collectionPaymentList).isNotNull().hasSize(2)
                .extracting("driverId", "groupId", "storeId", "cash", "pos", "clientId")
                .containsExactlyInAnyOrder(
                        tuple(315L, 50L, 20L, BigDecimal.valueOf(34), BigDecimal.valueOf(0), 20000L),
                        tuple(315L, 50L, 20L, BigDecimal.valueOf(45), BigDecimal.valueOf(0), 20000L)
                );
    }
}
