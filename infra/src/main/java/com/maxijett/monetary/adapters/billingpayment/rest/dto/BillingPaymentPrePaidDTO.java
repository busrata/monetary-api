package com.maxijett.monetary.adapters.billingpayment.rest.dto;

import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentPrePaidCreate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingPaymentPrePaidDTO {

    private Long storeId;

    private BigDecimal pos;

    private PayloadType payloadType;

    private Long clientId;

    private Long driverId;

    private Long groupId;

    private BigDecimal prePaidBillingAmount;

    private ZonedDateTime createOn;

    public BillingPaymentPrePaidCreate toUseCase() {
        return BillingPaymentPrePaidCreate.builder()
                .pos(getPos())
                .payloadType(getPayloadType())
                .clientId(getClientId())
                .storeId(getStoreId())
                .groupId(getGroupId())
                .driverId(getDriverId())
                .prePaidBillingAmount(getPrePaidBillingAmount())
                .createOn(getCreateOn())
                .build();
    }
}
