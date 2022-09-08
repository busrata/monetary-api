package com.maxijett.monetary.adapters.billingpayment.rest.dto;

import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentDelete;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillingPaymentDeleteDTO {

    private Long id;

    private String payingAccount;

    private PayloadType payloadType;

    public BillingPaymentDelete toUseCase() {
        return BillingPaymentDelete.builder()
                .id(id)
                .payingAccount(payingAccount)
                .payloadType(payloadType)
                .build();

    }

}
