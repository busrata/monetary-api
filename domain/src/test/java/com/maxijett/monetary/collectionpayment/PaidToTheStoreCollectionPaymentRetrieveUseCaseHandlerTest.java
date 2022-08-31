package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.collectionpayment.adapters.CollectionPaymentFakeDataAdapter;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.useCase.PaidToTheStoreCollectionPaymentRetrieve;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class PaidToTheStoreCollectionPaymentRetrieveUseCaseHandlerTest {

    CollectionPaymentFakeDataAdapter collectionPaymentPort = new CollectionPaymentFakeDataAdapter();

    PaidToTheStoreCollectionPaymentRetrieveUseCaseHandler handler = new PaidToTheStoreCollectionPaymentRetrieveUseCaseHandler(collectionPaymentPort);

    @Test
    public void shouldBeReturnCollectionPaymentListWhenCashPaymentExistOnStores() {
        //Given
        PaidToTheStoreCollectionPaymentRetrieve useCase = PaidToTheStoreCollectionPaymentRetrieve.builder()
                .driverId(1L)
                .groupId(20L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();

        //When
        List<CollectionPayment> list = handler.handle(useCase);

        //Then
        assertThat(list).isNotNull().hasSize(2)
                .extracting("driverId", "groupId", "cash", "pos")
                .containsExactlyInAnyOrder(
                        tuple(1L, 20L, new BigDecimal("34"), BigDecimal.ZERO),
                        tuple(1L, 20L, new BigDecimal("45"), BigDecimal.ZERO)
                );

    }
}
