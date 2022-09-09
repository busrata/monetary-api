package com.maxijett.monetary.collectionreport.useCase;

import com.maxijett.monetary.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionReportMonthlyByStoreRetrieve implements UseCase {

    private Long storeId;

    private LocalDate requestDate;

}
