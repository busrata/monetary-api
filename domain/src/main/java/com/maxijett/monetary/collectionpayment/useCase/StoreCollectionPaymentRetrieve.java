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
public class StoreCollectionPaymentRetrieve implements UseCase {

    private Long storeId;

    private LocalDate requestDate;

    public static StoreCollectionPaymentRetrieve fromModel(Long storeId, LocalDate requestDate) {
        return StoreCollectionPaymentRetrieve.builder()
                .storeId(storeId)
                .requestDate(requestDate)
                .build();
    }

}
