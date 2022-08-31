package com.maxijett.monetary.collectionpayment.useCase;

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
public class PaidToTheStoreCollectionPaymentRetrieve implements UseCase {

    private Long driverId;

    private Long groupId;

    private LocalDate startDate;

    private LocalDate endDate;

}
