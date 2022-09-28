package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.collectionpayment.adapters.CollectionPaymentFakeDataAdapter;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentRetrieveByDateRangeAndStore;
import com.maxijett.monetary.collectionreport.adapters.ShiftTimeFakeDataAdapter;
import com.maxijett.monetary.collectionreport.port.ShiftTimePort;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class GetAllCollectionPaymentListByDateBetweenAndStoreUseCaseHandlerTest {

    CollectionPaymentPort collectionPaymentPort = new CollectionPaymentFakeDataAdapter();

    ShiftTimePort shiftTimePort = new ShiftTimeFakeDataAdapter();

    GetAllCollectionPaymentsByDateBetweenAndStoreUseCaseHandler useCaseHandler = new GetAllCollectionPaymentsByDateBetweenAndStoreUseCaseHandler(collectionPaymentPort, shiftTimePort);

    @Test
    public void shouldRetrieveCollectionPaymentsByStoreIdAndDate() {

        //Given
        CollectionPaymentRetrieveByDateRangeAndStore useCase = CollectionPaymentRetrieveByDateRangeAndStore.builder()
                .storeId(57L)
                .startDate(LocalDate.now().minusDays(1L))
                .endDate(LocalDate.now().plusDays(2L))
                .build();

        //When
        List<CollectionPayment> collectionPaymentList = useCaseHandler.handle(useCase);

        //Then
        assertThat(collectionPaymentList).isNotNull().hasSize(1)
                .extracting("storeId", "cash", "pos")
                .containsExactlyInAnyOrder(
                        tuple(57L, BigDecimal.TEN, BigDecimal.valueOf(65))
                );

    }
}
