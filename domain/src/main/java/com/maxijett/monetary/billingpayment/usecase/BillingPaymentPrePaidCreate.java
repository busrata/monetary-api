package com.maxijett.monetary.billingpayment.usecase;

import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillingPaymentPrePaidCreate implements UseCase {

    private Long storeId;

    private PaymentType paymentType;

    private PayloadType payloadType;

    private Long clientId;

    private Long driverId;

    private BigDecimal prePaidBillingAmount;

    private Long groupId;
}
