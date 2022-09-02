package com.maxijett.monetary.billingpayment.usecase;

import com.maxijett.monetary.common.model.UseCase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillingPaymentListGet implements UseCase {

    private ZonedDateTime createOn;

    private Long groupId;

    public static BillingPaymentListGet fromModel(Long groupId, ZonedDateTime createOn){
        return BillingPaymentListGet.builder().groupId(groupId).createOn(createOn).build();
    }
}
