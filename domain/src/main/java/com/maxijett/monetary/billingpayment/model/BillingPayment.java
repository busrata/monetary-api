package com.maxijett.monetary.billingpayment.model;

import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingPayment implements Serializable {

    private Long id;

    private Long storeId;

    private BigDecimal cash;

    private BigDecimal pos;

    private String payingAccount;

    private PayloadType payloadType;

    private Long clientId;

    private Boolean isDeleted;

    private ZonedDateTime createOn;

    private Long groupId;

}
