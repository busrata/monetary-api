package com.maxijett.monetary.billingpayment.usecase;

import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillingPaymentCreate implements UseCase {

    private Long storeId;

    private BigDecimal amount;

    private PaymentType paymentType;

    private String payingAccount;

    private PayloadType payloadType;

    private Long clientId;

    private ZonedDateTime createOn;

    private Long groupId;

}
