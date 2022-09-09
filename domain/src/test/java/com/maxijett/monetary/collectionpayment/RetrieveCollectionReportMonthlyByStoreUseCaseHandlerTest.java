package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.collectionreport.RetrieveCollectionReportMonthlyByStoreUseCaseHandler;
import com.maxijett.monetary.collectionreport.adapters.CollectionReportFakeDataAdapter;
import com.maxijett.monetary.collectionreport.adapters.ShiftTimeFakeDataAdapter;
import com.maxijett.monetary.collectionreport.model.CollectionReport;
import com.maxijett.monetary.collectionreport.useCase.CollectionReportMonthlyByStoreRetrieve;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class RetrieveCollectionReportMonthlyByStoreUseCaseHandlerTest {

    CollectionReportFakeDataAdapter collectionReportPort = new CollectionReportFakeDataAdapter();

    ShiftTimeFakeDataAdapter shiftTimePort = new ShiftTimeFakeDataAdapter();
    RetrieveCollectionReportMonthlyByStoreUseCaseHandler handler = new RetrieveCollectionReportMonthlyByStoreUseCaseHandler(collectionReportPort, shiftTimePort);

    @Test
    public void shouldReturnMonthlyCollectionReportListByStore() {
        //Given
        CollectionReportMonthlyByStoreRetrieve useCase = CollectionReportMonthlyByStoreRetrieve.builder()
                .storeId(20L)
                .requestDate(LocalDate.now())
                .build();

        //When
        List<CollectionReport> collectionReportList = handler.handle(useCase);

        //Then
        assertThat(collectionReportList).isNotNull().hasSize(2)
                .extracting("storeId", "cash", "pos", "clientId")
                .containsExactlyInAnyOrder(
                        tuple(57L, BigDecimal.valueOf(10), BigDecimal.valueOf(1), 20000L),
                        tuple(57L, BigDecimal.valueOf(0), BigDecimal.valueOf(10), 20000L)
                );
    }
}
