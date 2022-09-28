package com.maxijett.monetary.adapters.billingpayment.rest.dto;

import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingPaymentDTO {

    private Long storeId;

    private BigDecimal cash;

    private BigDecimal pos;

    private String payingAccount;

    private PayloadType payloadType;

    private Long clientId;

    private ZonedDateTime createOn;

    private Long groupId;

    public BillingPaymentCreate toUseCase() {
        return BillingPaymentCreate.builder()
                .cash(getCash())
                .pos(getPos())
                .payloadType(getPayloadType())
                .payingAccount(getPayingAccount())
                .clientId(getClientId())
                .storeId(getStoreId())
                .createOn(getCreateOn())
                .groupId(getGroupId())
                .build();
    }

}
