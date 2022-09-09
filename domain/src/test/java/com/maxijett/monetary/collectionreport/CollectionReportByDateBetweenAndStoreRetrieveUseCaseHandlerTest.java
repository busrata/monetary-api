package com.maxijett.monetary.collectionreport;

import com.maxijett.monetary.collectionreport.adapters.CollectionReportFakeDataAdapter;
import com.maxijett.monetary.collectionreport.adapters.ShiftTimeFakeDataAdapter;
import com.maxijett.monetary.collectionreport.model.CollectionReport;
import com.maxijett.monetary.collectionreport.port.CollectionReportPort;
import com.maxijett.monetary.collectionreport.port.ShiftTimePort;
import com.maxijett.monetary.collectionreport.useCase.CollectionReportByDateBetweenAndStoreRetrieve;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class CollectionReportByDateBetweenAndStoreRetrieveUseCaseHandlerTest {

    CollectionReportPort collectionReportPort = new CollectionReportFakeDataAdapter();
    ShiftTimePort shiftTimePort = new ShiftTimeFakeDataAdapter();
    CollectionReportDateRangeByStoreRetrieveUseCaseHandler handler = new CollectionReportDateRangeByStoreRetrieveUseCaseHandler(collectionReportPort, shiftTimePort);

    @Test
    public void shouldReturnCollectionReportDateRangeByStore() {

        //Given
        CollectionReportByDateBetweenAndStoreRetrieve useCase = CollectionReportByDateBetweenAndStoreRetrieve.builder()
                .storeId(20L)
                .startDate(LocalDate.of(2022, 6, 12))
                .endDate(LocalDate.of(2022, 6, 20))
                .build();

        //When
        List<CollectionReport> collectionReportList = handler.handle(useCase);

        //Then
        assertThat(collectionReportList).isNotNull().hasSize(2)
                .extracting("storeId", "clientId", "cash", "pos")
                .containsExactlyInAnyOrder(
                        tuple(57L, 20000L, BigDecimal.valueOf(10), BigDecimal.valueOf(1)),
                        tuple(57L, 20000L, BigDecimal.valueOf(0), BigDecimal.valueOf(10))
                );
    }

}
