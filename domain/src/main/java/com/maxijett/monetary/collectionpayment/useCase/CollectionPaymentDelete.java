package com.maxijett.monetary.collectionpayment.useCase;

import com.maxijett.monetary.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CollectionPaymentDelete implements UseCase {
    Long id;

    public static CollectionPaymentDelete fromModel(Long id){
        return CollectionPaymentDelete.builder()
                .id(id)
                .build();

    }
}
