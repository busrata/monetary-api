package com.maxijett.monetary.collectionpayment.useCase;

import com.maxijett.monetary.common.model.UseCase;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollectionPaymentListGet implements UseCase{

    private ZonedDateTime createOn;

    private Long groupId;

    public static CollectionPaymentListGet fromModel(Long groupId, ZonedDateTime createOn){
        return CollectionPaymentListGet.builder().groupId(groupId).createOn(createOn).build();
    }
}

