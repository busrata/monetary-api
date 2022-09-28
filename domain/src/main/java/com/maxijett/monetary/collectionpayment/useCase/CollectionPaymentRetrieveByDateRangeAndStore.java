package com.maxijett.monetary.collectionpayment.useCase;

import com.maxijett.monetary.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CollectionPaymentRetrieveByDateRangeAndStore implements UseCase {

    private Long storeId;

    private LocalDate startDate;

    private LocalDate endDate;

}
