package com.maxijett.monetary.collectionpayment.useCase;

import com.maxijett.monetary.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollectionPaymentListGet implements UseCase {

    private ZonedDateTime createOn;

    private Long groupId;

}

